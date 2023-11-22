package com.bee.product.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
@Component
public class MessageSender {
    @Autowired
    private JmsTemplate jmsTemplate;
    public void sendMessage(String message){
        jmsTemplate.convertAndSend("bee.queue", message);
    }
}