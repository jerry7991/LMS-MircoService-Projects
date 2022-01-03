package com.lms.book.api;

import java.util.List;

import com.lms.book.dto.BookDto;
import com.lms.book.exceptions.BookNotFoundException;
import com.lms.book.exceptions.DuplicateBookException;

public interface BookServices {
	public List<BookDto> getAllBooks();

	public BookDto findById(Integer id) throws BookNotFoundException;

	public BookDto addBook(BookDto bookDto) throws DuplicateBookException;

	public boolean deleteById(Integer id) throws BookNotFoundException;

	public BookDto updateBook(Integer id, BookDto bookDto) throws BookNotFoundException;
}
