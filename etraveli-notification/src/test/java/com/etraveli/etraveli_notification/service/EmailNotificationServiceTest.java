package com.etraveli.etraveli_notification.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import com.etraveli.notification.model.TemperaturEvent;
import com.etraveli.notification.service.EmailNotificationSErvice;

class EmailNotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationSErvice emailNotificationService;

    private TemperaturEvent testEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testEvent = new TemperaturEvent();
        testEvent.setToken("test@example.com");
        testEvent.setLocation("Test Location");
        testEvent.setThreshold(30);
        testEvent.setTemp(35);
    }

    @Test
    void testNotify_Success() throws Exception {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailNotificationService.notify(testEvent);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void testNotify_ExceptionHandling() throws Exception {
        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Email server down"));

        assertDoesNotThrow(() -> emailNotificationService.notify(testEvent));
    }
}

