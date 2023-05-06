package com.dataart.jac.webinar.howtotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.function.Supplier;

@SpringBootApplication
public class HowtotestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HowtotestApplication.class, args);
	}

	public Supplier<String> sayHello() {
		return () -> "Hello";
	}

	@GetMapping("/do-a-test")
	public String launchDarklyTest() {
		return "This is a test";
	}

}
