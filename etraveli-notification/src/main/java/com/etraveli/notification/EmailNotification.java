package com.etraveli.notification;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailNotification {
	 private final JavaMailSender mailSender;

	    public EmailNotification(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }

	    public void sendSimpleEmail(String to, String subject, String text) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, false);

	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(text, true); // true for HTML content

	            mailSender.send(message);
	            System.out.println("Email sent successfully to " + to);
	        } catch (Exception e) {
	            System.err.println("Error sending email: " + e.getMessage());
	        }
	    }
}
