package com.lms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.UserServices;
import com.lms.dto.UserDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserClientControllerTest {
	@MockBean
	UserServices userService;
	@Autowired
	MockMvc mockMvc;

	@MockBean
	RestTemplate restTemplate;

	@Test
	@DisplayName("getAllUsers should return all user with ok status")
	void getAllUserShouldReturnAllUsersWithOkStatus() throws Exception {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.get("/library/users/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("getUser should return user by userName with Ok Status")
	void getUserShouldReturnUserByUserNameWithOkStatus() throws Exception {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.get("/library/users/anup").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("saveUser should save user with ok status")
	void saveUserShouldSaveUserWithOkStatus() throws Exception {
		UserDto user = new UserDto();
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/").content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("updateUser should update user and give ok status")
	void updateUserShouldUpdateUserIfExistAndGiveOkStatus() throws Exception {
		UserDto user = new UserDto();
		when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.put("/library/users/anup").content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteUser should delete user with ok status")
	void deleteUserShouldDeleteUserIfExistWithOkStatus() throws Exception {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.delete("/library/users/anup").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
