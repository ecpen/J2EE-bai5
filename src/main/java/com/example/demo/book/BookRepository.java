package com.example.demo.book;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("""
			select b
			from Book b
			where lower(b.title) like concat('%', lower(:keyword), '%')
			   or lower(b.author) like concat('%', lower(:keyword), '%')
			order by b.id desc
			""")
	List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);

	Optional<Book> findByIsbn(String isbn);

	boolean existsByIsbn(String isbn);

	boolean existsByIsbnAndIdNot(String isbn, Long id);
}