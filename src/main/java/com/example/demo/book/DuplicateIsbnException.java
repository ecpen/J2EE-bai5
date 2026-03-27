package com.example.demo.book;

public class DuplicateIsbnException extends RuntimeException {

	public DuplicateIsbnException(String isbn) {
		super("Mã sách đã tồn tại: " + isbn);
	}
}