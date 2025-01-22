package com.etraveli.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceFactory {

	@Autowired
    private ApplicationContext applicationContext;
	
	
	/**
	 * Factory method to return a appropriate NotificationService based on channel provided.
	 * @param channel
	 * @return
	 */
	public NotificationService getNotificationService(String channel) {
		if (channel.equalsIgnoreCase("email")) {
			return applicationContext.getBean(EmailNotificationSErvice.class);
		} else if (channel.equalsIgnoreCase("sms")) {
			return applicationContext.getBean(SmsNotificationService.class);
		}else {
			return null;
		}
	}
}
