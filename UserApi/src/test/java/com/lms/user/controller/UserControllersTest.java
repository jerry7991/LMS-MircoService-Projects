package com.lms.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.user.api.UserServices;
import com.lms.user.dto.UserDto;
import com.lms.user.exceptions.DuplicateUserException;
import com.lms.user.exceptions.UserNotFoundException;
import com.lms.user.repository.UserRepository;

@WebMvcTest(UserControllers.class)
@ContextConfiguration(classes = { UserControllers.class })
class UserControllersTest {

	@MockBean
	UserServices userServices;

	@MockBean
	UserRepository userRepository;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("getAllUsers should return all user with ok status")
	void getAllUserShouldReturnAllUsersWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/add").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("getUser should return user by userName with Ok Status")
	void getUserShouldReturnUserByUserNameWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/anup").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("getUserByUserName should not found status if user not exist")
	void getUserByUserNameShouldReturnNotFoundStatusIfUserNotExist() throws Exception {
		when(userServices.findByUserName(anyString())).thenThrow(new UserNotFoundException("User doesn't exit."));
		String errMsg = "";
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/users/anup").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		} catch (NestedServletException ex) {
			errMsg = ex.getMessage();
		}
		assertEquals("User doesn't exit.", errMsg.substring(errMsg.length() - "User doesn't exit.".length()));
	}

	@Test
	@DisplayName("saveUser should save user with ok status")
	void saveUserShouldSaveUserWithOkStatus() throws Exception {
		UserDto user = new UserDto("anup", "anup", "anup", "anup");
		mockMvc.perform(MockMvcRequestBuilders.post("/users/add").content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("saveUser should conflict status if user already exist")
	void saveUserShouldReturnConflictStatusIfUserAlreadyExist() throws Exception {
		doThrow(DuplicateUserException.class).when(userServices).addUser(any());
		UserDto user = new UserDto("anup", "anup", "anup", "anup");
		try {
			mockMvc.perform(MockMvcRequestBuilders.post("/users/add").content(asJsonString(user))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
		} catch (NestedServletException ex) {
			DuplicateUserException exception = assertThrows(DuplicateUserException.class, () -> {
				throw ex.getCause();
			});
			assertNull(exception.getMessage());
		}
	}

	@Test
	@DisplayName("updateUser should throw exception if user not exist")
	void updateUserShouldGiveNotFoundStatusIfUserNotExist() throws Exception {
		doThrow(UserNotFoundException.class).when(userServices).updateUser(anyString(), any());
		UserDto user = new UserDto("anup", "anup", "anup", "anup");
		try {
			mockMvc.perform(MockMvcRequestBuilders.put("/users/singh").content(asJsonString(user))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		} catch (NestedServletException ex) {
			UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
				throw ex.getCause();
			});
			assertNull(exception.getMessage());
		}
	}

	@Test
	@DisplayName("updateUser should update user and give ok status")
	void updateUserShouldUpdateUserIfExistAndGiveOkStatus() throws Exception {
		UserDto user = new UserDto("anup", "anup", "anup", "anup");
		mockMvc.perform(MockMvcRequestBuilders.put("/users/singh").content(asJsonString(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteUser should throw exception if user not exist")
	void deleteUserShouldNotFoundStatusIfUserNotExist() throws Exception {
		doThrow(UserNotFoundException.class).when(userServices).deleteByName(anyString());
		try {
			mockMvc.perform(MockMvcRequestBuilders.delete("/users/anup").contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		} catch (NestedServletException ex) {
			UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
				throw ex.getCause();
			});
			assertNull(exception.getMessage());
		}

	}

	@Test
	@DisplayName("deleteUser should delete user with ok status")
	void deleteUserShouldDeleteUserIfExistWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/users/anup").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
