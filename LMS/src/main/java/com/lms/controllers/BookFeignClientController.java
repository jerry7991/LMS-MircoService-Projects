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

import com.lms.api.LibraryService;
import com.lms.clients.BookServiceClient;
import com.lms.dto.BookDto;
import com.lms.exceptions.BookIdNotMatchedException;

@RestController
@RequestMapping("/feign/library/books")
public class BookFeignClientController {

	@Autowired
	LibraryService libraryService;

	@Autowired
	BookServiceClient bookServiceClient;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<BookDto>> getAllBooks() {

		return bookServiceClient.getAllBooks();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BookDto> findById(@PathVariable Integer id) {
		return bookServiceClient.findById(id);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
		return bookServiceClient.addBook(bookDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Boolean> deleteById(@PathVariable Integer id) throws BookIdNotMatchedException {
		return bookServiceClient.deleteById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
		return bookServiceClient.updateBook(id, bookDto);
	}
}
