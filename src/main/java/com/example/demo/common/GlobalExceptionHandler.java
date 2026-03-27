package com.example.demo.common;

import com.example.demo.book.BookNotFoundException;
import com.example.demo.book.DuplicateIsbnException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleBookNotFound(BookNotFoundException exception) {
		return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage(), Map.of());
	}

	@ExceptionHandler(DuplicateIsbnException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicateIsbn(DuplicateIsbnException exception) {
		return buildResponse(HttpStatus.CONFLICT, exception.getMessage(), Map.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
		Map<String, String> validationErrors = new LinkedHashMap<>();
		for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return buildResponse(HttpStatus.BAD_REQUEST, "Dữ liệu nhập không hợp lệ", validationErrors);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleGenericException(Exception exception) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), Map.of());
	}

	private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus status, String message,
			Map<String, String> validationErrors) {
		ApiErrorResponse response = new ApiErrorResponse(
				LocalDateTime.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				validationErrors);
		return ResponseEntity.status(status).body(response);
	}
}