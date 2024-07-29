package com.aayush.proxies;

import com.aayush.proxies.annotation.MyTransactional;
import com.aayush.proxies.service.CustomerService;
import com.aayush.proxies.service.DefaultCustomerService;
import java.lang.reflect.Proxy;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringProxiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProxiesApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			var target = new DefaultCustomerService();

			var proxyInstance = (CustomerService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
				target.getClass().getInterfaces(),
				(proxy, method, methodArgs) -> {
					try {
						if (method.getAnnotation(MyTransactional.class) != null) {
							System.out.println("Transaction started");
						}
						return method.invoke(target, methodArgs);
					} finally {
						if (method.getAnnotation(MyTransactional.class) != null) {
							System.out.println("Transaction committed");
						}
					}
				}
			);

			proxyInstance.create();
		};
	}
}
