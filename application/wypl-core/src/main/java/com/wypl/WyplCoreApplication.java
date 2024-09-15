package com.wypl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.wypl"})
@EnableMongoRepositories(basePackages = {"com.wypl"})
public class WyplCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WyplCoreApplication.class, args);
	}

}
