package com.lms.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.user.api.UserServices;
import com.lms.user.dto.UserDto;
import com.lms.user.exceptions.DuplicateUserException;
import com.lms.user.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/users")
public class UserControllers {

	@Autowired
	UserServices userService;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> findAllUser() {
		return userService.findAllUser();
	}

	@GetMapping("/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto findByUserName(@PathVariable String userName) throws UserNotFoundException {
		return userService.findByUserName(userName);
	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.OK)
	public UserDto addUser(@RequestBody UserDto userDto) throws DuplicateUserException {
		return userService.addUser(userDto);
	}

	@DeleteMapping("/{userName}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public boolean deleteByName(@PathVariable String userName) throws UserNotFoundException {
		return userService.deleteByName(userName);
	}

	@PutMapping("/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public UserDto updateUser(@PathVariable String userName, @RequestBody UserDto userDto)
			throws UserNotFoundException, DuplicateUserException {
		return userService.updateUser(userName, userDto);
	}

}
