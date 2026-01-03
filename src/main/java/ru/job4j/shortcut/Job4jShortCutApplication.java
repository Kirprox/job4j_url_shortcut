package ru.job4j.shortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Job4jShortCutApplication {
	public static void main(String[] args) {
		SpringApplication.run(Job4jShortCutApplication.class, args);
		System.out.println("http://localhost:8080/swagger-ui/index.html");
	}
}
