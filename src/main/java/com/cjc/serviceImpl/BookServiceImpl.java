package com.cjc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.naming.directory.DirContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cjc.model.Book;
import com.cjc.repository.BookRepository;
import com.cjc.service.BookService;



@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void saveBook(Book book) {
		Book dbbook = bookRepository.save(book);

	}

	@Override
	public Book getBookById(int id) {

		if (bookRepository.existsById(id)) {
			Book dbBook = bookRepository.findById(id).get();
			return dbBook;
		}

		return null;
	}

	@Override
	public List<Book> findAllBooks() {

		return bookRepository.findAll();
	}

	@Override
	public void deleteBook(int id) {
		bookRepository.deleteById(id);

	}

	@Override
	public Book updateBook(int id, Book book) {

		if (bookRepository.existsById(id)) {
			Book dbBook = bookRepository.findById(id).get();
			dbBook.setName(book.getName());
			dbBook.setAuthor(book.getAuthor());
			dbBook.setPrice(book.getPrice());
			Book updatedBook = bookRepository.save(dbBook);
			return updatedBook;
		}

		return null;
	}

	@Override
	public Book editBookById(int id, Book book) {
		if (bookRepository.existsById(id)) {
			Book dbBook = bookRepository.findById(id).get();
			if (book.getName() != null) {
				dbBook.setName(book.getName());
			}
			if (book.getAuthor() != null) {
				dbBook.setAuthor(book.getAuthor());
			}
			if (book.getPrice() != null) {
				dbBook.setPrice(book.getPrice());
			}
			Book editedBook = bookRepository.save(dbBook);
			return editedBook;
		}
		return null;
	}

	@Override
	public List<Book> getBooksByPagination(int pageNo, int pageSize) {

		Pageable page = PageRequest.of(pageNo, pageSize);
		Page data = bookRepository.findAll(page);
		if (data.hasContent()) {
			List<Book> bookList = data.getContent();
			return bookList;

		}
		return null;
	}
	
	@Override
	public List<Book> getBooksSortByPrice(String direction) {
		
		Sort  sort;
		if(direction !=null &&  direction.equalsIgnoreCase("desc")) {
		
			sort=Sort.by(Direction.DESC, "price");
		}
		else {
			sort=Sort.by(Direction.ASC,"price");
		}
		return bookRepository.findAll(sort);
	}
	
	@Override
	public List<Book> getBookByName(String name) {
              List<Book> bookList = bookRepository.findByName(name);
              if(! bookList.isEmpty()) {
            	  return bookList;
              }
		
		return null;
	}
	@Override
	public List<Book> getBooksByFilter(Map<String, String> filters) {
		String author = filters.get("author");
		Double minPrice = filters.get("minPrice")!=null ? Double.valueOf(filters.get("minPrice")):null;
		Double maxPrice = filters.get("maxPrice")!=null ? Double.valueOf(filters.get("maxPrice")):null;
		return bookRepository.findByAuthorOrPriceBetween(author,minPrice,maxPrice);
	}
}
