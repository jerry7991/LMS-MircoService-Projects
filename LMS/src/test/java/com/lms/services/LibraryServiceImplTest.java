package com.lms.services;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
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

import com.lms.api.BookServices;
import com.lms.api.UserServices;
import com.lms.dto.LibraryDto;
import com.lms.entity.Library;
import com.lms.exceptions.BookIdNotMatchedException;
import com.lms.exceptions.IssuedBookOutOfBoundException;
import com.lms.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
class LibraryServiceImplTest {

	@Mock
	UserServices userService;
	@Mock
	BookServices bookService;
	@Mock
	LibraryRepository libraryRepository;
	@Mock
	ModelMapper modelMapper;

	@InjectMocks
	LibraryServiceImpl libraryService;
	Library library;
	LibraryDto libraryDto;

	@Before
	void setUp() {
		when(modelMapper.map(library, LibraryDto.class)).thenReturn(libraryDto);
	}

	@BeforeEach
	void init() {
		library = new Library(1, "asd", 1);
		libraryDto = new LibraryDto(1, "asd", 1);
	}

	@Test
	@DisplayName("saveLibrary should save library")
	void issueBookToUser() {
		LibraryDto library = new LibraryDto(1, "anup", 2);
		when(libraryRepository.countByBookId(anyInt())).thenReturn(BigInteger.valueOf(0));
		when(libraryRepository.countByUserName(anyString())).thenReturn(BigInteger.valueOf(0));
		Assertions.assertDoesNotThrow(() -> libraryService.issueBookToUser(library));
	}

	@Test
	@DisplayName("saveLibrary should throw exception if limit exceeded")
	void issueBookToUserShouldThrowExceptionIfLimitExceeded() {
		LibraryDto library = new LibraryDto(1, "anup", 2);
		when(libraryRepository.countByBookId(anyInt())).thenReturn(BigInteger.valueOf(0));
		when(libraryRepository.countByUserName(anyString())).thenReturn(BigInteger.valueOf(10));
		Assertions.assertThrows(IssuedBookOutOfBoundException.class, () -> libraryService.issueBookToUser(library));
	}

	@Test
	@DisplayName("deleteLibrary should throw exception if not found")
	void releaseBookFromUserShouldThrowExceptionIfNotFound() {
		Assertions.assertThrows(BookIdNotMatchedException.class, () -> libraryService.releaseBookFromUser(1));
	}

	@Test
	@DisplayName("deleteLibrary should delete")
	void releaseBookFromUserShouldDelete() {
		when(libraryRepository.findByBookId(anyInt())).thenReturn(Optional.of(library));
		Assertions.assertDoesNotThrow(() -> libraryService.releaseBookFromUser(1));
	}

	@Test
	@DisplayName("getAllLibraryByUserName should give libraries by username")
	void getAllLibraryByUserNameShouldFiveLibrariesByUserName() {
		when(libraryRepository.findAll()).thenReturn(List.of(library));
		Assertions.assertDoesNotThrow(() -> libraryService.findAllUsersWithIssuedBook());
	}

	@Test
	@DisplayName("deleteByBookId should delete by book id")
	void deleteByBookIdShouldDeleteByBookId() {
		when(libraryRepository.countByUserName(anyString())).thenReturn(BigInteger.valueOf(0));
		Assertions.assertDoesNotThrow(() -> libraryService.releaseAllBookFromUsers("anup"));
	}

}
