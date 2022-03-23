package com.lms.services;

import java.math.BigInteger;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.api.LibraryService;
import com.lms.dto.LibraryDto;
import com.lms.entity.Library;
import com.lms.exceptions.BookAlreadyIssuedException;
import com.lms.exceptions.BookIdNotMatchedException;
import com.lms.exceptions.IssuedBookOutOfBoundException;
import com.lms.repository.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	LibraryRepository libraryRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public LibraryDto issueBookToUser(LibraryDto libraryDto)
			throws BookAlreadyIssuedException, IssuedBookOutOfBoundException {
		if (libraryRepository.findByBookId(libraryDto.getBookId()).isPresent()) {
			throw new BookAlreadyIssuedException("Book is already issued with other users.");
		}

		if (libraryRepository.countByUserName(libraryDto.getUserName()).compareTo(BigInteger.valueOf(2)) > 0) {
			throw new IssuedBookOutOfBoundException("User can get atmost 3 book from library at any time.");
		}

		Library newData = modelMapper.map(libraryDto, Library.class);
		return modelMapper.map(libraryRepository.save(newData), LibraryDto.class);
	}

	@Override
	public boolean releaseBookFromUser(int bookId) throws BookIdNotMatchedException {
		Library library = libraryRepository.findByBookId(bookId).orElseThrow(BookIdNotMatchedException::new);
		libraryRepository.delete(library);
		return libraryRepository.findByBookId(bookId).isEmpty();
	}

	@Override
	public List<Library> findAllUsersWithIssuedBook() {
		return libraryRepository.findAll();
	}

	@Override
	public boolean releaseAllBookFromUsers(String userName) {
		libraryRepository.deleteAllByUserName(userName);
		return libraryRepository.countByUserName(userName).compareTo(BigInteger.valueOf(0)) == 0;
	}

}
