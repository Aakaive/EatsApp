package com.sparta.eatsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EatsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatsAppApplication.class, args);
    }

}
