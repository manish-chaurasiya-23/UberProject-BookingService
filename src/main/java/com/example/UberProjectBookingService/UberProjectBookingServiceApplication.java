package com.example.UberProjectBookingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
@EntityScan("com.example.UberProjectEntityService.models")
public class UberProjectBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UberProjectBookingServiceApplication.class, args);
	}

}
