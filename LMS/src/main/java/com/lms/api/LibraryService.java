package com.lms.api;

import java.util.List;

import com.lms.dto.LibraryDto;
import com.lms.entity.Library;
import com.lms.exceptions.BookAlreadyIssuedException;
import com.lms.exceptions.BookIdNotMatchedException;
import com.lms.exceptions.IssuedBookOutOfBoundException;

public interface LibraryService {
	LibraryDto issueBookToUser(LibraryDto libraryDto) throws BookAlreadyIssuedException, IssuedBookOutOfBoundException;

	boolean releaseBookFromUser(int bookId) throws BookIdNotMatchedException;

	List<Library> findAllUsersWithIssuedBook();

	boolean releaseAllBookFromUsers(String userName);
}