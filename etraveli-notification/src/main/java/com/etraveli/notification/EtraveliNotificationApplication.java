package com.etraveli.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.etraveli")
public class EtraveliNotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EtraveliNotificationApplication.class, args);
	}

}
