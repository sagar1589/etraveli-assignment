package com.etraveli.users.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PreferencesDetails {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long id;
	@NotNull(message="Location cannot be null")
	private String location;
	@NotNull(message="Threshold cannot be null")
	private Integer threshold;
    @NotNull(message="Email/Mobile cannot be null")
    private String token;
    @NotNull(message="Channel cannot be null")
	private String channel;
}
