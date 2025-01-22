package com.etraveli.users.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreferencesRepository extends JpaRepository<Preference, Long> {

	 @Query("Select i from Preference i where i.location = ?1 and i.threshold<=?2")
	public List<Preference> findPreferencesByLocationAndThreshold(String location, Integer threshold);
}
