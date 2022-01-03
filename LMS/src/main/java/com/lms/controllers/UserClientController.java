package com.lms.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lms.dto.UserDto;

@RestController
@RequestMapping("/library/users")
public class UserClientController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${external.service.usersURI}")
	String usersUri;

	@GetMapping("/")
	public String findAllUser() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		return restTemplate.exchange(usersUri + "/", HttpMethod.GET, entity, String.class).getBody();
	}

	@GetMapping("/{userName}")
	public String findByUserName(@PathVariable String userName) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		return restTemplate.exchange(usersUri + "/" + userName, HttpMethod.GET, entity, String.class).getBody();
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/")
	public String addUser(@RequestBody UserDto userDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<UserDto> entity = new HttpEntity<>(userDto, headers);
		return restTemplate.exchange(usersUri + "/add", HttpMethod.POST, entity, String.class).getBody();
	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping("/{userName}")
	public String deleteByName(@PathVariable String userName) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(usersUri + "/" + userName, HttpMethod.DELETE, entity, String.class).getBody();
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/{userName}")
	public String updateUser(@PathVariable String userName, @RequestBody UserDto userDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(usersUri + "/" + userName, HttpMethod.PUT, entity, String.class).getBody();
	}

}
