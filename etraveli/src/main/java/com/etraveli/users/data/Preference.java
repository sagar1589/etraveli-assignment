package com.etraveli.users.data;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Preference {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 50)
	private String location;
	
	@Column(nullable = false)
	private Integer threshold;

	@Column(nullable = false, length = 50)
	private String token;
	
	@Column(nullable = false, length = 50)
	private String channel;

	@Column(nullable = false)
	private Date modifiedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

}
