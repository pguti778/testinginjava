package com.dataart.jac.webinar.howtotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.function.Supplier;

@EnableFeignClients
@SpringBootApplication
public class HowtotestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HowtotestApplication.class, args);
	}

	@Bean
	public Supplier<String> sayHello() {
		return () -> "Hello";
	}


}
