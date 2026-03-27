package com.example.demo.book;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<Book> getAllBooks(@RequestParam(name = "keyword", required = false) String keyword) {
		return bookService.searchBooks(keyword);
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PostMapping
	public ResponseEntity<Book> createBook(@Valid @RequestBody BookRequest request) {
		Book createdBook = bookService.createBook(request);
		return ResponseEntity.created(URI.create("/api/books/" + createdBook.getId())).body(createdBook);
	}

	@PutMapping("/{id}")
	public Book updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
		return bookService.updateBook(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}