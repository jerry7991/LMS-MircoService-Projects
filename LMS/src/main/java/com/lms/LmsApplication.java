package com.lms;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@PropertySource("classpath:app.properties")
public class LmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
