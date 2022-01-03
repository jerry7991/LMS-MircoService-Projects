package com.lms.api;

import com.lms.dto.UserDto;

public interface UserServices {
	String findAllUser();

	UserDto findByUserName(String name);

	String addUser(UserDto userDto);

	String deleteByName(String name);

	String updateUser(String name, UserDto userDto);
}