package com.lms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.api.LibraryService;
import com.lms.dto.LibraryDto;
import com.lms.entity.Library;
import com.lms.exceptions.BookAlreadyIssuedException;
import com.lms.exceptions.BookIdNotMatchedException;
import com.lms.exceptions.IssuedBookOutOfBoundException;

@RestController
@RequestMapping("/library/")
public class LibraryControllers {

	@Autowired
	LibraryService libraryService;

	@PostMapping("users/books")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public LibraryDto issueBookToUser(@RequestBody LibraryDto libraryDto)
			throws BookAlreadyIssuedException, IssuedBookOutOfBoundException {

		return libraryService.issueBookToUser(libraryDto);
	}

	@DeleteMapping("users/books/{bookId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public boolean releaseBookFromUser(@PathVariable int bookId) throws BookIdNotMatchedException {
		return libraryService.releaseBookFromUser(bookId);
	}

	@GetMapping("users/books/mapped-by-issued")
	@ResponseStatus(HttpStatus.OK)
	public List<Library> findAllUsersWithIssuedBook() {
		return libraryService.findAllUsersWithIssuedBook();
	}

	@DeleteMapping("users/books/clear-user/{userName}")
	@ResponseStatus(HttpStatus.OK)
	public boolean releaseAllBookFromUsers(@PathVariable String userName) {
		return libraryService.releaseAllBookFromUsers(userName);
	}

}
