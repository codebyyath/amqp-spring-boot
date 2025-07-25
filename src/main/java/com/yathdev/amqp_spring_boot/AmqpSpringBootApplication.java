package com.yathdev.amqp_spring_boot;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class AmqpSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmqpSpringBootApplication.class, args);
	}

}
