package com.example.demo.book;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Book> searchBooks(String keyword) {
		if (keyword == null || keyword.isBlank()) {
			return bookRepository.findAll();
		}
		return bookRepository.searchByTitleOrAuthor(keyword.trim());
	}

	public Book createBook(BookRequest request) {
		String normalizedIsbn = request.getIsbn().trim();
		if (bookRepository.existsByIsbn(normalizedIsbn)) {
			throw new DuplicateIsbnException(normalizedIsbn);
		}

		Book book = new Book();
		applyRequest(book, request, normalizedIsbn);
		return bookRepository.save(book);
	}

	public Book updateBook(Long id, BookRequest request) {
		Book book = getBookById(id);
		String normalizedIsbn = request.getIsbn().trim();
		if (bookRepository.existsByIsbnAndIdNot(normalizedIsbn, id)) {
			throw new DuplicateIsbnException(normalizedIsbn);
		}

		applyRequest(book, request, normalizedIsbn);
		return bookRepository.save(book);
	}

	public void deleteBook(Long id) {
		Book book = getBookById(id);
		bookRepository.delete(book);
	}

	private void applyRequest(Book book, BookRequest request, String normalizedIsbn) {
		book.setTitle(request.getTitle().trim());
		book.setAuthor(request.getAuthor().trim());
		book.setIsbn(normalizedIsbn);
		book.setPublisher(request.getPublisher() == null ? null : request.getPublisher().trim());
		book.setPrice(request.getPrice());
		book.setPublishedYear(request.getPublishedYear());
		book.setCategory(request.getCategory() == null ? null : request.getCategory().trim());
		book.setQuantityInStock(request.getQuantityInStock());
	}
}