package com.lms.user.api;

import java.util.List;

import com.lms.user.dto.UserDto;
import com.lms.user.exceptions.DuplicateUserException;
import com.lms.user.exceptions.UserNotFoundException;

public interface UserServices {
	List<UserDto> findAllUser();

	UserDto findByUserName(String name) throws UserNotFoundException;

	UserDto addUser(UserDto userDto) throws DuplicateUserException;

	boolean deleteByName(String name) throws UserNotFoundException;

	UserDto updateUser(String name, UserDto userDto) throws UserNotFoundException, DuplicateUserException;
}