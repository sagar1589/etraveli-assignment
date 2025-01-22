package com.etraveli.etraveli.service;

import com.etraveli.users.data.Preference;
import com.etraveli.users.data.User;
import com.etraveli.users.data.UserRepository;
import com.etraveli.users.models.PreferencesDetails;
import com.etraveli.users.models.UserDetail;
import com.etraveli.users.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private User userEntity;
    @Mock
    private UserDetail userDetail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDetail = new UserDetail();
        userDetail.setId(1L);
        userDetail.setName("sagar");

        PreferencesDetails preferenceDetail = new PreferencesDetails();
        preferenceDetail.setId(1L);
        preferenceDetail.setChannel("email");
        preferenceDetail.setToken("abc@example.com");
        preferenceDetail.setLocation("pune");
        preferenceDetail.setThreshold(40);
        List<PreferencesDetails> preferencesDetailsList = new ArrayList<>();
        preferencesDetailsList.add(preferenceDetail);
        userDetail.setPreferences(preferencesDetailsList);

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setName("sagar");

        Preference preferenceEntity = new Preference();
        preferenceEntity.setId(1L);
        preferenceEntity.setChannel("email");
        preferenceEntity.setToken("abc@example.com");
        preferenceEntity.setLocation("pune");
        preferenceEntity.setThreshold(40);
        preferenceEntity.setModifiedDate(new Date());
        preferenceEntity.setUser(userEntity);
        userEntity.setPreferences(List.of(preferenceEntity));
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserDetail result = userService.createUpdateUser(userDetail, false);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("sagar", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testUpdateUser() {
    	userEntity.setId(1L);
    	userEntity.setName("mayur");
    	Optional<User> userEntityOptional = Optional.of(userEntity);
    	
    	when(userRepository.save(any(User.class))).thenReturn(userEntity);
        when(userRepository.findById(anyLong())).thenReturn(userEntityOptional);
        userDetail.setId(1L);
        userDetail.setName("mayur");

        UserDetail result = userService.createUpdateUser(userDetail, true);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("mayur", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUser() {
        when(userRepository.getReferenceById(1L)).thenReturn(userEntity);

        UserDetail result = userService.getUser(1L);

        assertNotNull(result);
        verify(userRepository, times(1)).getReferenceById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = List.of(userEntity);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getUserAllUsers(1L);

        assertNotNull(result);
        verify(userRepository, times(1)).findAll();
    }
}
