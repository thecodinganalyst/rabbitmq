package com.hevlar.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqApplication {
	
	private static final boolean NON_DURABLE = false;
	private static final String MY_QUEUE_NAME = "myQueue";

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(RabbitTemplate rabbitTemplate){
		return args -> {
			rabbitTemplate.convertAndSend(MY_QUEUE_NAME, "Hello, World");
		};
	}
	
	@Bean
	public Queue myQueue(){
		return new Queue(MY_QUEUE_NAME, NON_DURABLE);
	}
	
	@RabbitListener(queues = MY_QUEUE_NAME)
	public void listen(String in){
		System.out.println("Message read from " + MY_QUEUE_NAME + ": " + in);
	}
}
