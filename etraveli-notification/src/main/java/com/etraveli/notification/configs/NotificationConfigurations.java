package com.etraveli.notification.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class NotificationConfigurations {
	 @Value("${twilio.account.sid}")
	    private String accountSid;

	    @Value("${twilio.auth.token}")
	    private String authToken;

	    @PostConstruct
	    public void initTwilio() {
	        Twilio.init(accountSid, authToken);
	    }
}
