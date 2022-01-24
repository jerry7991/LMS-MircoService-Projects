package com.lms.book.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.book.api.BookServices;
import com.lms.book.dto.BookDto;
import com.lms.book.exceptions.BookNotFoundException;
import com.lms.book.exceptions.DuplicateBookException;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookControllers.class)
class BookControllersTest {
	@MockBean
	BookServices bookServices;
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("getAllBooks should return all Book with ok status")
	void getAllBooksShouldReturnAllBooksWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/books/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("getBook should return Book by Id with Ok Status")
	void getBookShouldReturnBookByIdWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("getBookByID should not found status if Book not exist")
	void getBookByIdShouldReturnNotFoundStatusIfBookNotExist() throws Exception {
		doThrow(BookNotFoundException.class).when(bookServices).findById(anyInt());
		mockMvc.perform(MockMvcRequestBuilders.get("/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("saveBook should save Book with ok status")
	void saveBookShouldSaveBookWithOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "", "", "");
		mockMvc.perform(MockMvcRequestBuilders.post("/books/add").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	@DisplayName("saveBook should conflict status if Book already exist")
	void saveBookShouldReturnConflictStatusIfBookAlreadyExist() throws Exception {
		doThrow(DuplicateBookException.class).when(bookServices).addBook(any());
		BookDto bookDto = new BookDto(1, "", "", "");
		mockMvc.perform(MockMvcRequestBuilders.post("/books/add").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}

	@Test
	@DisplayName("updateBook should throw exception if Book not exist")
	void updateBookShouldGiveNotFoundStatusIfBookNotExist() throws Exception {
		doThrow(BookNotFoundException.class).when(bookServices).updateBook(anyInt(), any());
		BookDto bookDto = new BookDto(1, "", "", "");
		mockMvc.perform(MockMvcRequestBuilders.put("/books/1").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("updateBook should update Book and give ok status")
	void updateBookShouldUpdateBookIfExistAndGiveOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "", "", "");
		mockMvc.perform(MockMvcRequestBuilders.put("/books/1").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteBook should throw exception if Book not exist")
	void deleteBookShouldNotFoundStatusIfBookNotExist() throws Exception {
		doThrow(BookNotFoundException.class).when(bookServices).deleteById(anyInt());
		mockMvc.perform(MockMvcRequestBuilders.delete("/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("deleteBook should delete Book with ok status")
	void deleteBookShouldDeleteBookIfExistWithOkStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
