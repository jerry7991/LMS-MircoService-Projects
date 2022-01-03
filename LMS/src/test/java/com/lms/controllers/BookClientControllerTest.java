package com.lms.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.api.BookServices;
import com.lms.api.LibraryService;
import com.lms.api.UserServices;
import com.lms.dto.BookDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:app.properties")
class BookClientControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	BookServices bookServices;
	@MockBean
	UserServices userServices;

	@MockBean
	LibraryService libraryService;

	@MockBean
	RestTemplate restTemplate;

	@Value("${external.service.booksURI}")
	String booksUri;

	@Test
	@DisplayName("getAllBooks should return all Book with ok status")
	void getAllBooksShouldReturnAllBooksWithOkStatus() throws Exception {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/books/");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
		verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(),
				ArgumentMatchers.<Class<String>>any());
	}

	@Test
	@DisplayName("getBook should return Book by Id with Ok Status")
	void findByIdShouldReturnBook() throws Exception {
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.get("/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("addBook should save Book with ok status")
	void addBookShouldSaveBookWithOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "anup", "anup", "anup");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/books/add").contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(bookDto))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("updateBook should update Book and give ok status")
	void updateBookShouldUpdateBookIfExistAndGiveOkStatus() throws Exception {
		BookDto bookDto = new BookDto(1, "anup", "anup", "anup");
		when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.put("/library/books/1").content(asJsonString(bookDto))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	@DisplayName("deleteBook should delete Book with ok status")
	void deleteBookShouldDeleteBookIfExistWithOkStatus() throws Exception {
		when(libraryService.releaseBookFromUser(anyInt())).thenReturn(true);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.DELETE), any(), ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>("[]", HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.delete("/library/books/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	static String asJsonString(final Object obj) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
