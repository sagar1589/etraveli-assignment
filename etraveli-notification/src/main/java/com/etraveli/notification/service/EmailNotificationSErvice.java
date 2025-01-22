package com.etraveli.notification.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.etraveli.notification.model.TemperaturEvent;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotificationSErvice implements NotificationService {

	 private final JavaMailSender mailSender;

	    public EmailNotificationSErvice(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	
	/**
	 * Sends email notifications to provided email id in the event.
	 */
	@Override
	public void notify(TemperaturEvent event) {
		 try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, false);

	            helper.setTo(event.getToken());
	            helper.setSubject("Temperature notification for " + event.getLocation());
	            helper.setText("Temperature has risen above " + event.getThreshold() + ", it's " + event.getTemp(), false);

	            mailSender.send(message);
	            System.out.println("Email sent successfully to " + event.getToken());
	        } catch (Exception e) {
	            System.err.println("Error sending email: " + e.getMessage());
	        }
	}

}
