package com.etraveli.users.data;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
		
	    @Column(nullable = false, length = 50, unique = true)
	    private String name;
	   
	    @JsonIgnore
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private Collection<Preference> preferences;
	    
}
