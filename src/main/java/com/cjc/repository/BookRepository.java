package com.cjc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByName(String name);

	List<Book> findByAuthorOrPriceBetween(String author, Double minPrice, Double maxPrice);

}
