package com.zyra.zyra_travel_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zyra"})
@EntityScan("com.zyra")
@EnableJpaRepositories("com.zyra")
public class ZyraTravelServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZyraTravelServiceApplication.class, args);
    }
}