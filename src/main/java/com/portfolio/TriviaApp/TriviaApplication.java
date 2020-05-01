package com.portfolio.TriviaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.portfolio.TriviaApp")
public class TriviaApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(TriviaApplication.class, args);
	}

}
