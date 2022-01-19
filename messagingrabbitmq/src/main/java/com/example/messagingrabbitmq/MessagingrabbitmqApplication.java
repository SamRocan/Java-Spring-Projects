package com.example.messagingrabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingrabbitmqApplication {

	static final String topicExchangeName = "spring-boot-exchange";

	static final String queueName = "spring-boot";

	// creates an AMQP queue.
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	// creates a topic exchange
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicExchangeName);
	}
	// binds these queue and exchange together, defining the behavior that occurs when
	// RabbitTemplate publishes to an exchange
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	/*registered as a message listener in the container. listens for messages on the spring-boot queue.
	 Because the Receiver class is a POJO, it needs to be wrapped in the MessageListenerAdapter, where
	  you specify that it invokes receiveMessage*/
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	/* uses Spring Boot’s SpringApplication.run() method to launch an application. */

	/*
	On mac by this command you can start,restart or stop rabbitmq

	brew services start rabbitmq
	brew services stop  rabbitmq
	brew services restart rabbitmq

	 */
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MessagingrabbitmqApplication.class, args).close();
	}

}
