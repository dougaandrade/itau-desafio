package com.itau.itau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ItauApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItauApplication.class, args);
	}

}
