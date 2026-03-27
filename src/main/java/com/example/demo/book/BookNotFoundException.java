package com.example.demo.book;

public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(Long id) {
		super("Không tìm thấy sách với id = " + id);
	}
}