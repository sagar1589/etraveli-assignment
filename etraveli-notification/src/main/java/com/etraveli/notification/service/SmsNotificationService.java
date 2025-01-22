package com.etraveli.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.etraveli.notification.model.TemperaturEvent;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsNotificationService implements NotificationService {


	@Value("${twilio.phone.number}")
    private String fromPhoneNumber;

	/**
	 * Sends text notifications to provided mobile number in the event.
	 */
	@Override
	public void notify(TemperaturEvent event) {
		 Message sms = Message.creator(
	                new PhoneNumber(event.getToken()),
	                new PhoneNumber(fromPhoneNumber),
	                "Temperature has risen above " + event.getThreshold() + ", it's " + event.getTemp()
	        ).create();
	        System.out.println("SMS sent successfully!");
	}

}
