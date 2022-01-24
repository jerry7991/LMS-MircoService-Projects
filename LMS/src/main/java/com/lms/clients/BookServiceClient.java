package com.lms.clients;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lms.dto.BookDto;

//@FeignClient(name = "BookService", url = "${external.service.booksURI}")
@FeignClient(name = "BOOK-SERVICE")
@LoadBalancerClient(name = "BOOK-SERVICE")
public interface BookServiceClient {
	@GetMapping("/books/")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<BookDto>> getAllBooks();

	@GetMapping("/books/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BookDto> findById(@PathVariable Integer id);

	@PostMapping("/books/")
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto);

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Boolean> deleteById(@PathVariable Integer id);

	@PutMapping("/books/{id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto);
}
