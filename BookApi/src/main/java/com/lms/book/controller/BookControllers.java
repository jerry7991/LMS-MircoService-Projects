package com.lms.book.controller;

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

import com.lms.book.api.BookServices;
import com.lms.book.dto.BookDto;
import com.lms.book.exceptions.BookNotFoundException;
import com.lms.book.exceptions.DuplicateBookException;

@RestController
@RequestMapping("/books")
public class BookControllers {

	@Autowired
	BookServices bookServices;

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<BookDto> getAllBooks() {
		return bookServices.getAllBooks();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BookDto findById(@PathVariable Integer id) throws BookNotFoundException {
		return bookServices.findById(id);
	}

	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public BookDto addBook(@RequestBody BookDto bookDto) throws DuplicateBookException {
		return bookServices.addBook(bookDto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Boolean deleteById(@PathVariable Integer id) throws BookNotFoundException {
		return bookServices.deleteById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BookDto updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) throws BookNotFoundException {
		return bookServices.updateBook(id, bookDto);
	}
}
