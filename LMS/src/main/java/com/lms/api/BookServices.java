package com.lms.api;

import com.lms.dto.BookDto;
import com.lms.exceptions.BookIdNotMatchedException;

public interface BookServices {
	public String getAllBooks();

	public String findById(Integer id);

	public String addBook(BookDto bookDto);

	public String deleteById(Integer id) throws BookIdNotMatchedException;

	public String updateBook(Integer id, BookDto bookDto);

}