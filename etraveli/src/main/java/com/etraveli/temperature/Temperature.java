package com.etraveli.temperature;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.etraveli.temperature.model.TemperaturEvent;
import com.etraveli.users.data.Preference;
import com.etraveli.users.data.PreferencesRepository;

/**
 * 
 */
@Service
public class Temperature {
	
	@Value("${spring.kafka.topic.name}")
    private String topicName;
	
	@Autowired
	private PreferencesRepository preferencesRepository;
	
	private final KafkaTemplate<String, TemperaturEvent> kafkaTemplate;
	
	
	 public Temperature(KafkaTemplate<String, TemperaturEvent> kafkaTemplate) {
	        this.kafkaTemplate = kafkaTemplate;
	    }
	 
	/**
	 * Executes cron job every 5 minutes to get the whether details of pune.
	 */
	@Scheduled(cron = "0 */2 * * * *")
	public void getPuneTemperatureUpdate() {
		System.out.println("Executing cron job to get pune's temeperature");
		int puneTemp = getMeanTemp();
		evaluateAndPush("pune", puneTemp);
		
	}
	
	/**
	 * Executes cron job every 5 minutes to get the whether details of bangalore.
	 */
	@Scheduled(cron = "0 1/2 * * * *")
	public void getBangaloreTemperatureUpdate() {
		System.out.println("Executing cron job to get bangalore's temeperature");
		int bangaloreTemp = getMeanTemp();
		evaluateAndPush("bangalore", bangaloreTemp);
		
	}
	
	/**
	 * Evaluates the eligibility to send notification and push the event to kafka topic
	 * @param city
	 * @param temp
	 */
	private void evaluateAndPush(String city, int temp) {
		
		List<Preference> prefernces = preferencesRepository.findPreferencesByLocationAndThreshold(city, temp);
		for (Preference preference : prefernces) {
			if(checkEligibility(preference)) {
				TemperaturEvent event = new TemperaturEvent();
				event.setToken(preference.getToken());
				event.setName(preference.getUser().getName());
				event.setLocation(preference.getLocation());
				event.setThreshold(preference.getThreshold());
				event.setTemp(temp);
				event.setChannel(preference.getChannel());
				pushToTopic(event);
				preference.setModifiedDate(new Date());
				preferencesRepository.save(preference);
			}
			
		}
	}
	
	/**
	 * Checks the time passed since last notification sent to this user preference, 
	 * If its more than 2 hours, method returns true to indicate notification can be sent.
	 * @param preference
	 * @return
	 */
	private boolean checkEligibility(Preference preference) {	
		
		    Instant instant1 = preference.getModifiedDate().toInstant();
	        Instant instant2 = new Date().toInstant();
	        long hoursDifference = Duration.between(instant1, instant2).toMinutes();
	        if (hoursDifference >= 5) {
	        	System.out.println("Eligible for notification ");
	            return true;
	        } 
	        System.out.println("Not Eligible for notification ");
	        return false;
	}
	
	
	/**
	 * Push the temperature event to kafka topic
	 * @param event
	 */
	private void pushToTopic(TemperaturEvent event) {
		 kafkaTemplate.send(topicName, event);
		 System.out.println("Sent message: " + event.toString());
	}
	
	private int getMeanTemp() {
		return (getTempSourceOne()+getTempSourceTwo())/2;
	}

	private int getTempSourceOne() {
		Random random = new Random();
		int randomNumber = random.nextInt(41) + 20;
		return randomNumber;
	}

	private int getTempSourceTwo() {
		Random random = new Random();
		int randomNumber = random.nextInt(41) + 20;
		return randomNumber;
	}

}
