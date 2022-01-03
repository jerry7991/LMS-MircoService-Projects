package com.lms.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.book.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	boolean existsByName(String name);
}
