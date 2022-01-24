package com.lms.clients;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lms.dto.UserDto;

//@FeignClient(name = "UserService", url = "${external.service.usersURI}")
@FeignClient(name = "USERSERVICE")
@LoadBalancerClient(name = "USERSERVICE")
public interface UserServiceClient {

	@GetMapping("/users/")
	public ResponseEntity<List<UserDto>> findAllUser();

	@GetMapping("/users/{userName}")
	public ResponseEntity<UserDto> findByUserName(@PathVariable String userName);

	@PostMapping("/users/")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto);

	@DeleteMapping("/users/{userName}")
	public ResponseEntity<Boolean> deleteByName(@PathVariable String userName);

	@PutMapping("/users/{userName}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userName, @RequestBody UserDto userDto);

}
