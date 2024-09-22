package com.wypl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.wypl"})
public class WyplCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(WyplCoreApplication.class, args);
    }

}
