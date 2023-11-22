package com.bee.product.service;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Component
public class MessageReceiver {
    @JmsListener(destination = "bee.queue")
    public void receiveMessage(String message) {
        System.out.println("## Queue= bee.queue -> Received: <" + message + ">");
    }
}