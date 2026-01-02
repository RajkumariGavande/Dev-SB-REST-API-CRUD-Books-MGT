package com.cjc.service;

import java.util.List;
import java.util.Map;

import com.cjc.model.Book;

public interface BookService {

	void saveBook(Book book);

	Book getBookById(int id);

	List<Book> findAllBooks();

	void deleteBook(int id);

	Book updateBook(int id, Book book);

	Book editBookById(int id, Book book);

	List<Book> getBooksByPagination(int pageNo, int pageSize);

	List<Book> getBooksSortByPrice(String direction);

	List<Book> getBookByName(String name);

	List<Book> getBooksByFilter(Map<String, String> filters);

}
