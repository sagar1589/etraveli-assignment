package com.etraveli.notification.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.etraveli.notification.model.TemperaturEvent;
import com.etraveli.notification.service.NotificationService;
import com.etraveli.notification.service.NotificationServiceFactory;

@Service
public class Consumer {
	
	@Autowired
	private NotificationServiceFactory notificationServiceFactory;

	/**
	 * Listener for configured kafka topic, which process the message to send notifications
	 * @param message
	 */
	@KafkaListener(topics = "etraveliTopic", groupId = "etraveli-consumer-group")
    public void listen(TemperaturEvent message) {
        System.out.println("Received message: " + message);
        
        NotificationService notificationService = notificationServiceFactory.getNotificationService(message.getChannel());
        notificationService.notify(message);       
    }
}
