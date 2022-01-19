package com.example.messagingrabbitmq;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

/*
Receiver that responds to published messages.

The Receiver is a POJO that defines a method for receiving messages.
When you register it to receive messages, you can name it anything you want.
 */
@Component
public class Receiver {
    // work takes 1 second
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown(); //Decrements the count of the latch, releasing all waiting threads if the count reaches zero.
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}