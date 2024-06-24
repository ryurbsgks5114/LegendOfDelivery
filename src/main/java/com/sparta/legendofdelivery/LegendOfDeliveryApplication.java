package com.sparta.legendofdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class LegendOfDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegendOfDeliveryApplication.class, args);
	}

}
