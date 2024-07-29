package com.aayush.proxies;

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
					System.out.println("Before method call");
					var result = method.invoke(target, methodArgs);
					System.out.println("After method call");
					return result;
				}
			);

			proxyInstance.create();
		};
	}
}
