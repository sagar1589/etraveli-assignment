package com.etraveli.etraveli.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.etraveli.temperature.model.TemperaturEvent;
import com.etraveli.users.data.PreferencesRepository;
import com.etraveli.users.models.UserDetail;
import com.etraveli.users.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
    @MockBean
    private KafkaTemplate<String, TemperaturEvent> kafkaTemplate;
    
    @Mock
	private PreferencesRepository preferencesRepository;

    private UserDetail mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new UserDetail();
        mockUser.setId(1L);
        mockUser.setName("sagar");
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUpdateUser(any(UserDetail.class), eq(false))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"sagar\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("sagar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

        verify(userService, times(1)).createUpdateUser(any(UserDetail.class), eq(false));
    }
    
    
    
    
    @Test
    void testUpdateUser() throws Exception {
    	mockUser.setId(1L);
    	mockUser.setName("mayur");    	
        when(userService.createUpdateUser(any(UserDetail.class), eq(true))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"mayur\", \"id\":\"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("mayur"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

        verify(userService, times(1)).createUpdateUser(any(UserDetail.class), eq(true));
    }

    @Test
    void testGetUser() throws Exception {
        when(userService.getUser(1L)).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("sagar"));
                
        verify(userService, times(1)).getUser(1L);
    }
}
