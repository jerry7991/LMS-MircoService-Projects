package com.lms.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.BookServices;
import com.lms.api.LibraryService;
import com.lms.api.UserServices;
import com.lms.clients.BookServiceClient;
import com.lms.dto.BookDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:app.properties")
class BookFeignClientControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	BookServices bookServices;
	@MockBean
	UserServices userServices;

	@MockBean
	LibraryService libraryService;

	@MockBean
	BookServiceClient bookServiceClient;

	@Value("${external.service.booksURI}")
	String booksUri;

	@Test
	@DisplayName("getAllBooks should return all Book with ok status")
	void getAllBooksShouldReturnAllBooksWithOkStatus() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/feign/library/books/");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	@DisplayName("getBook should return Book by Id with Ok Status")
	void findByIdShouldReturnBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/feign/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("addBook should save Book with ok status")
	void addBookShouldSaveBookWithOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "anup", "anup", "anup");
		mockMvc.perform(MockMvcRequestBuilders.post("/feign/library/books/").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bookDto))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("updateBook should update Book and give ok status")
	void updateBookShouldUpdateBookIfExistAndGiveOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "anup", "anup", "anup");
		mockMvc.perform(MockMvcRequestBuilders.put("/feign/library/books/1").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteBook should delete Book with ok status")
	void deleteBookShouldDeleteBookIfExistWithOkStatus() throws Exception {
		when(libraryService.releaseBookFromUser(anyInt())).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete("/feign/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

	static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
