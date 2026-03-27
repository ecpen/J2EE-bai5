package com.example.demo.book;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.common.GlobalExceptionHandler;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class BookControllerTests {

	private MockMvc mockMvc;

	private BookService bookService;

	@BeforeEach
	void setUp() {
		bookService = mock(BookService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new BookController(bookService))
				.setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	void shouldReturnBookList() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Clean Code");
		book.setAuthor("Robert C. Martin");
		book.setIsbn("9780132350884");
		book.setPrice(BigDecimal.valueOf(150000));
		book.setQuantityInStock(10);

		when(bookService.searchBooks(null)).thenReturn(List.of(book));

		mockMvc.perform(get("/api/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Clean Code"))
				.andExpect(jsonPath("$[0].author").value("Robert C. Martin"));
	}

	@Test
	void shouldCreateBook() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setTitle("Spring in Action");
		book.setAuthor("Craig Walls");
		book.setIsbn("9781617297571");
		book.setPrice(BigDecimal.valueOf(200000));
		book.setQuantityInStock(5);

		when(bookService.createBook(any(BookRequest.class))).thenReturn(book);

		mockMvc.perform(post("/api/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  \"title\": \"Spring in Action\",
						  \"author\": \"Craig Walls\",
						  \"isbn\": \"9781617297571\",
						  \"publisher\": \"Manning\",
						  \"price\": 200000,
						  \"publishedYear\": 2023,
						  \"category\": \"Programming\",
						  \"quantityInStock\": 5
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.isbn").value("9781617297571"));
	}
}