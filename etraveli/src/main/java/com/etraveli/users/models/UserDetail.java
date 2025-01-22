package com.etraveli.users.models;

import java.util.Collection;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDetail {
	
    private Long id;
    @NotNull(message="Name cannot be null")
    private String name;
    private Collection<PreferencesDetails> preferences;
}
