package com.lms.user.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.lms.user.dto.UserDto;
import com.lms.user.entity.User;
import com.lms.user.exceptions.DuplicateUserException;
import com.lms.user.exceptions.UserNotFoundException;
import com.lms.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	@InjectMocks
	UserServiceImpl userService;
	@Mock
	UserRepository userRepository;
	@Mock
	ModelMapper modelMapper;
	UserDto userDto;
	User user;

	@Before
	void setUp() {
		when(modelMapper.map(userDto, User.class)).thenReturn(user);
		when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
	}

	@BeforeEach
	void init() {
		userDto = new UserDto("anup", "anup", "anup", "anup");
		user = new User("anup", "anup", "anup", "anup");
	}

	@Test
	@DisplayName("getAllUsers should return all user")
	void getAllUserShouldReturnAllUsers() {
		when(userRepository.findAll()).thenReturn(List.of(user, user));
		Assertions.assertFalse(userService.findAllUser().isEmpty());
	}

	@Test
	@DisplayName("getUser should return user by userName")
	void getUserShouldReturnUserByUserName() throws UserNotFoundException {
		when(userRepository.findById(anyString())).thenReturn(java.util.Optional.of(user));
		Assertions.assertNull(userService.findByUserName("anup"));
	}

	@Test
	@DisplayName("getUser should throw exception if user not exist")
	void getUserShouldThrowExceptionIfUserNotExist() {
		when(userRepository.findById(anyString())).thenReturn(java.util.Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.findByUserName("anup"));
	}

	@Test
	@DisplayName("addUser should save user")
	void addUserShouldSaveUser() {
		Assertions.assertDoesNotThrow(() -> userService.addUser(userDto));
	}

	@Test
	@DisplayName("addUser should throw exception if user already exist")
	void addUserShouldThrowExceptionIfUserAlreadyExist() {
		when(userRepository.existsById(anyString())).thenReturn(true);
		Assertions.assertThrows(DuplicateUserException.class, () -> userService.addUser(userDto));
	}

	@Test
	@DisplayName("updateUser should throw exception if user not exist")
	void updateUserShouldThrowExceptionIfUserNotExist() {
		when(userRepository.findById(anyString())).thenReturn(Optional.empty());
		Assertions.assertThrows(UserNotFoundException.class,
				() -> userService.updateUser(userDto.getUserName(), userDto));
	}

	@Test
	@DisplayName("updateUser should update user")
	void updateUserShouldUpdateUserIfExist() {
		when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
		Assertions.assertDoesNotThrow(() -> userService.updateUser(userDto.getUserName(), userDto));
	}

	@Test
	@DisplayName("deleteUser should throw exception if user not exist")
	void deleteUserShouldThrowExceptionIfUserNotExist() {
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteByName(user.getUserName()));
	}

	@Test
	@DisplayName("deleteUser should delete user")
	void deleteUserShouldDeleteUserIfExist() {
		when(userRepository.existsById(anyString())).thenReturn(true);
		Assertions.assertDoesNotThrow(() -> userService.deleteByName(user.getUserName()));
	}
}
