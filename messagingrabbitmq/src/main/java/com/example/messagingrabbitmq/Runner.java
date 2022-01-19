package com.example.messagingrabbitmq;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// test messages are sent by a CommandLineRunner
@Component
public class Runner implements CommandLineRunner{
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    //Sends this message to the receiver
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        //sends the message
        rabbitTemplate.convertAndSend(MessagingrabbitmqApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        //gets latch from receiver after 1 second (time the receiver takes)
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}

