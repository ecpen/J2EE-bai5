package com.example.demo.book;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class BookRequest {

	@NotBlank(message = "Tên sách không được để trống")
	@Size(max = 200, message = "Tên sách tối đa 200 ký tự")
	private String title;

	@NotBlank(message = "Tác giả không được để trống")
	@Size(max = 120, message = "Tác giả tối đa 120 ký tự")
	private String author;

	@NotBlank(message = "Mã sách không được để trống")
	@Size(max = 20, message = "Mã sách tối đa 20 ký tự")
	private String isbn;

	@Size(max = 120, message = "Nhà xuất bản tối đa 120 ký tự")
	private String publisher;

	@NotNull(message = "Giá không được để trống")
	@DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
	private BigDecimal price;

	@Min(value = 1900, message = "Năm xuất bản phải từ 1900 trở lên")
	@Max(value = 2100, message = "Năm xuất bản không hợp lệ")
	private Integer publishedYear;

	@Size(max = 80, message = "Thể loại tối đa 80 ký tự")
	private String category;

	@NotNull(message = "Số lượng tồn không được để trống")
	@PositiveOrZero(message = "Số lượng tồn phải lớn hơn hoặc bằng 0")
	private Integer quantityInStock;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getPublishedYear() {
		return publishedYear;
	}

	public void setPublishedYear(Integer publishedYear) {
		this.publishedYear = publishedYear;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
}