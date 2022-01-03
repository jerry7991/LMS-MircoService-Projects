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

import com.lms.api.LibraryService;
import com.lms.dto.BookDto;
import com.lms.exceptions.BookIdNotMatchedException;

@RestController
@RequestMapping("/library/books")
public class BookClientController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	LibraryService libraryService;

	@Value("${external.service.booksURI}")
	String booksUri;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public String getAllBooks() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(booksUri + "/", HttpMethod.GET, entity, String.class).getBody();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String findById(@PathVariable Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(booksUri + "/" + id, HttpMethod.GET, entity, String.class).getBody();
	}

	@PostMapping("/add")
	@ResponseStatus(HttpStatus.OK)
	public String addBook(@RequestBody BookDto bookDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BookDto> entity = new HttpEntity<>(bookDto, headers);
		return restTemplate.exchange(booksUri + "/add", HttpMethod.POST, entity, String.class).getBody();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteById(@PathVariable Integer id) throws BookIdNotMatchedException {
		libraryService.releaseBookFromUser(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(booksUri + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<BookDto> entity = new HttpEntity<>(bookDto, headers);
		return restTemplate.exchange(booksUri + "/" + id, HttpMethod.PUT, entity, String.class).getBody();
	}
}
