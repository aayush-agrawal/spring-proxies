package com.aayush.proxies;

import com.aayush.proxies.service.CustomerService;
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
	ApplicationRunner runner(CustomerService customerService) {
		return args -> customerService.create();
	}

}
