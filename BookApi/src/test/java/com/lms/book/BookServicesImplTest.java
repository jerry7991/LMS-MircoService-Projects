package com.lms.book;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.lms.book.dto.BookDto;
import com.lms.book.entity.Book;
import com.lms.book.exceptions.BookNotFoundException;
import com.lms.book.exceptions.DuplicateBookException;
import com.lms.book.repository.BookRepository;
import com.lms.book.service.BookServicesImpl;

@ExtendWith(MockitoExtension.class)
class BookServicesImplTest {
	@Mock
	BookRepository bookRepository;
	@Mock
	ModelMapper modelMapper;

	@InjectMocks
	@Autowired
	BookServicesImpl bookService;
	BookDto bookDto;
	Book book;

	@Before
	void setUp() {
		when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
		when(modelMapper.map(book, BookDto.class)).thenReturn(bookDto);
	}

	@BeforeEach
	void init() {

		bookDto = new BookDto(1, "aaa", "aaa", "aaa");
		book = new Book(1, "aaa", "aaa", "aaa");
	}

	@Test
	@DisplayName("getAllBooks should return all Books")
	void getAllBooksShouldReturnAllBooks() {
		when(bookRepository.findAll()).thenReturn(List.of(book, book));
		Assertions.assertFalse(bookService.getAllBooks().isEmpty());
	}

	@Test
	@DisplayName("getBook should return Book by Id")
	void getBookShouldReturnBookById() throws BookNotFoundException {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		Assertions.assertNull(bookService.findById(1));
	}

	@Test
	@DisplayName("getBook should throw exception if Book not exist")
	void getBookShouldThrowExceptionIfBookNotExist() {
		when(bookRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.findById(1));
	}

	@Test
	@DisplayName("saveBook should save Book")
	void saveBookShouldSaveBook() {
		Assertions.assertDoesNotThrow(() -> bookService.addBook(bookDto));
	}

	@Test
	@DisplayName("saveBook should throw exception if Book already exist")
	void saveBookShouldThrowExceptionIfBookAlreadyExist() {
		when(bookRepository.existsByName(anyString())).thenReturn(true);
		Assertions.assertThrows(DuplicateBookException.class, () -> bookService.addBook(bookDto));
	}

	@Test
	@DisplayName("updateBook should throw exception if Book not exist")
	void updateBookShouldThrowExceptionIfBookNotExist() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookDto.getId(), bookDto));
	}

	@Test
	@DisplayName("updateBook should update Book")
	void updateBookShouldUpdateBookIfExist() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		Assertions.assertDoesNotThrow(() -> bookService.updateBook(bookDto.getId(), bookDto));
	}

	@Test
	@DisplayName("deleteBook should throw exception if Book not exist")
	void deleteBookShouldThrowExceptionIfBookNotExist() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.empty());
		Assertions.assertThrows(BookNotFoundException.class, () -> bookService.deleteById(1));
	}

	@Test
	@DisplayName("deleteBook should delete Book")
	void deleteBookShouldDeleteBookIfExist() {
		when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));
		Assertions.assertDoesNotThrow(() -> bookService.deleteById(1));
	}
}
