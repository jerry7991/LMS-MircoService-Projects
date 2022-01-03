package com.lms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.BookServices;
import com.lms.api.LibraryService;
import com.lms.api.UserServices;
import com.lms.dto.LibraryDto;
import com.lms.exceptions.BookIdNotMatchedException;
import com.lms.exceptions.IssuedBookOutOfBoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LibraryControllers.class)
class LibraryControllersTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	LibraryService libraryService;
	@MockBean
	UserServices userService;
	@MockBean
	BookServices bookService;

	@Test
	@DisplayName("saveLibrary should save Library with ok status")
	void issueBookToUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/books/").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new LibraryDto(2, "abc", 2)))).andExpect(status().isAccepted());
	}

	@Test
	@DisplayName("saveLibrary should give error for limit exceeded with Conflict status")
	void issueBookToUserShouldGiveErrorForLimitExceededWithConflictStatus() throws Exception {
		doThrow(IssuedBookOutOfBoundException.class).when(libraryService).issueBookToUser(any(LibraryDto.class));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/books/").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(new LibraryDto(2, "abc", 2)))).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("releaseBookFromUser should delete Library with ok status")
	void releaseBookFromUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/library/users/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
	}

	@Test
	@DisplayName("deleteLibrary should give error if not exist with not found status")
	void deleteLibraryShouldGiveErrorIfNotExistWithNotFoundStatus() throws Exception {
		doThrow(BookIdNotMatchedException.class).when(libraryService).releaseBookFromUser(anyInt());
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/library/users/jerry/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);

	}
}
