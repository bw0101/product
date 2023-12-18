package com.bee.product.service;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Component
public class MessageReceiver {
    @JmsListener(destination = "bee.queue")
    public void receiveMessage(String message) {
        System.out.print("## Queue= bee.queue -> Received: <" + message + ">");
    }
}