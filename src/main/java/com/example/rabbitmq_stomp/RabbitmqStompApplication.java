package com.example.rabbitmq_stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RabbitmqStompApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqStompApplication.class, args);
	}

}
