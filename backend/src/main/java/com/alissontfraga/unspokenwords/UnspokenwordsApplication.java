package com.alissontfraga.unspokenwords;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UnspokenwordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnspokenwordsApplication.class, args);
	}

}
