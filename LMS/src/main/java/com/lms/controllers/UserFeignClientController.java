package com.lms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.clients.UserServiceClient;
import com.lms.dto.UserDto;

@RestController
@RequestMapping("/feign/library/users")
public class UserFeignClientController {

	@Autowired
	UserServiceClient userServiceClient;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<UserDto>> findAllUser() {
		return userServiceClient.findAllUser();
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserDto> findByUserName(@PathVariable String userName) {
		return userServiceClient.findByUserName(userName);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		return userServiceClient.addUser(userDto);
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{userName}")
	public ResponseEntity<Boolean> deleteByName(@PathVariable String userName) {
		return userServiceClient.deleteByName(userName);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userName}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userName, @RequestBody UserDto userDto) {
		return userServiceClient.updateUser(userName, userDto);
	}
}
