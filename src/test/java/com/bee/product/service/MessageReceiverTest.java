package com.bee.product.service;
import com.bee.product.service.MessageReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MessageReceiverTest {
    private MessageReceiver messageReceiver;

    @BeforeEach
    public void setUp() {
        messageReceiver = new MessageReceiver();
    }

    @Test
    public void testReceiveMessage() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent)); // redirects console output to outContent

        String testMessage = "Test Message";
        messageReceiver.receiveMessage(testMessage);

        assertEquals("## Queue= bee.queue -> Received: <Test Message>", outContent.toString());

        System.setOut(System.out);  // returns System.out back to normal
    }
}
