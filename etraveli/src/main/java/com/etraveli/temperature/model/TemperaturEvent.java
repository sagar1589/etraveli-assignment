package com.etraveli.temperature.model;

import lombok.Data;

@Data
public class TemperaturEvent {
	    private String token;
	    private String name;
	    private String location;
		private Integer threshold;
		private Integer temp;
		private String channel;
}
