package com.aayush.proxies;

import com.aayush.proxies.annotation.MyTransactional;
import com.aayush.proxies.service.CustomerService;
import com.aayush.proxies.service.DefaultCustomerService;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
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
			var pf = new ProxyFactory();
			pf.setTarget(target);
			pf.setInterfaces( target.getClass().getInterfaces() );
			MethodInterceptor interceptor = invocation -> {
				var method = invocation.getMethod();
				var arguments = invocation.getArguments();
				try {
					if (method.getAnnotation(MyTransactional.class) != null) {
						System.out.println("Transaction started");
					}
					return method.invoke(target, arguments);
				} finally {
					if (method.getAnnotation(MyTransactional.class) != null) {
						System.out.println("Transaction committed");
					}
				}
			};
			pf.addAdvice(interceptor);
			var proxyInstance = (CustomerService) pf.getProxy();
			proxyInstance.create();
		};
	}
}
