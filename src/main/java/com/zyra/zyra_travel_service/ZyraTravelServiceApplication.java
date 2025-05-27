package com.zyra.zyra_travel_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZyraTravelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZyraTravelServiceApplication.class, args);
    }
}