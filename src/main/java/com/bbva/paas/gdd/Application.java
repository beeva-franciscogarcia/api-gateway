package com.bbva.paas.gdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
		b.featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).build();
		return b;
	}
}