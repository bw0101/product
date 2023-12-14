package com.bee.product.service;

import com.bee.product.service.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.*;

public class MessageSenderTest {

    private MessageSender messageSender;
    private JmsTemplate jmsTemplate;

    @BeforeEach
    public void setUp() {
        messageSender = new MessageSender();
        jmsTemplate = mock(JmsTemplate.class);
        ReflectionTestUtils.setField(messageSender, "jmsTemplate", jmsTemplate);
    }

    @Test
    public void testSendMessage() {
        String testMessage = "Test Message";
        messageSender.sendMessage(testMessage);
        verify(jmsTemplate, times(1)).convertAndSend("bee.queue", testMessage);
    }
}
