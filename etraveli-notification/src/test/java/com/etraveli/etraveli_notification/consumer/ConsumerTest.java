package com.etraveli.etraveli_notification.consumer;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.etraveli.notification.consumer.Consumer;
import com.etraveli.notification.model.TemperaturEvent;
import com.etraveli.notification.service.NotificationService;
import com.etraveli.notification.service.NotificationServiceFactory;

@ExtendWith(MockitoExtension.class)
class ConsumerTest {

    @Mock
    private NotificationServiceFactory notificationServiceFactory;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private Consumer consumer;

    @Test
    void testListenEmail() {
        TemperaturEvent mockEvent = new TemperaturEvent();
        mockEvent.setChannel("EMAIL");
        mockEvent.setToken("test@example.com");
        mockEvent.setLocation("New York");
        mockEvent.setTemp(30);
        mockEvent.setThreshold(25);
        
        when(notificationServiceFactory.getNotificationService("EMAIL")).thenReturn(notificationService);
        consumer.listen(mockEvent);
        
        verify(notificationServiceFactory, times(1)).getNotificationService("EMAIL");
        verify(notificationService, times(1)).notify(mockEvent);
    }

    @Test
    void testListenInvalidChannel() {
        TemperaturEvent mockEvent = new TemperaturEvent();
        mockEvent.setChannel("INVALID");

        when(notificationServiceFactory.getNotificationService("INVALID")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> consumer.listen(mockEvent));
        
        verify(notificationServiceFactory, times(1)).getNotificationService("INVALID");
        verifyNoInteractions(notificationService);
    }
}
