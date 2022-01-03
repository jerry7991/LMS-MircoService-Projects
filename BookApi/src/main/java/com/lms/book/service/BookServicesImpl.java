package com.lms.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.book.api.BookServices;
import com.lms.book.dto.BookDto;
import com.lms.book.entity.Book;
import com.lms.book.exceptions.BookNotFoundException;
import com.lms.book.exceptions.DuplicateBookException;
import com.lms.book.repository.BookRepository;

@Service
public class BookServicesImpl implements BookServices {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll().stream().map(book -> modelMapper.map(book, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public BookDto findById(Integer id) throws BookNotFoundException {
		return modelMapper.map(bookRepository.findById(id).orElseThrow(BookNotFoundException::new), BookDto.class);
//		return modelMapper.map(
//				bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found exception.")),
//				BookDto.class);
	}

	@Override
	public BookDto addBook(BookDto bookDto) throws DuplicateBookException {
		if (bookRepository.existsByName(bookDto.getName())) {
			throw new DuplicateBookException("Book already present.");
		}
		Book book = modelMapper.map(bookDto, Book.class);
		return modelMapper.map(bookRepository.save(book), BookDto.class);
	}

	@Override
	public boolean deleteById(Integer id) throws BookNotFoundException {
		bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		bookRepository.deleteById(id);
		return bookRepository.findById(id).isEmpty();
	}

	@Override
	public BookDto updateBook(Integer id, BookDto bookDto) throws BookNotFoundException {
		Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
		book.setName(bookDto.getName());
		book.setAuthor(bookDto.getAuthor());
		book.setPublisher(bookDto.getPublisher());

		return modelMapper.map(bookRepository.save(book), BookDto.class);
	}

}
