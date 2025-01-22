package com.etraveli.users.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etraveli.exceptions.InvalidRequestException;
import com.etraveli.users.data.Preference;
import com.etraveli.users.data.User;
import com.etraveli.users.data.UserRepository;
import com.etraveli.users.models.PreferencesDetails;
import com.etraveli.users.models.UserDetail;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	/**
	 * Common method to create a user entry in the database or update the existing user details.
	 * @param user
	 * @param isUpdate - true if call is for update(PUT) user.
	 * @return
	 * @throws InvalidRequestException
	 */
	public UserDetail createUpdateUser(UserDetail user, boolean isUpdate) throws InvalidRequestException{
		
		ModelMapper modelMapper = new ModelMapper();
		User userEntity = modelMapper.map(user, User.class);
		
		//Check whether request is for create or update user.
		if(isUpdate) {
			if(null == user.getId()) {
				throw new InvalidRequestException("id can not be empty for PUT method");
			}
			User userEntityFound = userRepository.findById(user.getId()).get();
			userEntity.setId(userEntityFound.getId());
		}		
		List<Preference> preferences = new ArrayList<>();		 
		for (PreferencesDetails preferencesDetails : user.getPreferences()) {
			
			//Validates the channel value provided.
			if(!("email".equals(preferencesDetails.getChannel()) || "sms".equals(preferencesDetails.getChannel()))) {
				throw new InvalidRequestException("Invalid channel value entered");
			}
			Preference preferenceEntity = modelMapper.map(preferencesDetails, Preference.class);
			preferenceEntity.setUser(userEntity);
			LocalDate yesterdayLocalDate = LocalDate.now().minusDays(1);
		    Date yesterday = Date.from(yesterdayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			preferenceEntity.setModifiedDate(yesterday);
			preferences.add(preferenceEntity);
		}
		userEntity.setPreferences(preferences);
		userEntity = userRepository.save(userEntity);
		user.setId(userEntity.getId());
		return user;
	}
	
	/**
	 * retrieves users details for given id
	 * @param id
	 * @return returns user detail model object
	 */
	public UserDetail getUser(Long id) {
		User userEntity =  userRepository.getReferenceById(id);
		ModelMapper modelMapper = new ModelMapper();
		return  modelMapper.map(userEntity, UserDetail.class);
	}
	
	
	/**
	 * removes user details for given id. 
	 * @param id
	 * @return
	 */
	public boolean deleteUser(Long id) {
		try {
			userRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/**
	 * retrieves all the users from table
	 * @param id
	 * @return
	 */
	public List<User> getUserAllUsers(Long id) {
		return userRepository.findAll();
	}
}
