package com.termalabs.subscriptions;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DummyCustomerAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(DummyCustomerAPIApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Welcome to the Dummy Customer API:");


        };
    }

}