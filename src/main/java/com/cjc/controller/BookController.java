package com.cjc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.model.Book;
import com.cjc.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Book Management API", description = "CRUD operations, pagination, sorting, filtering for Books")
public class BookController {
	private BookService bookService;

	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	@Operation(summary = "Add a new book", description = "Creates a new book record in the database")
	@ApiResponse(responseCode = "200", description = "Book added successfully")
	@PostMapping("/addBook")
	public String addBook(@RequestBody Book book) {
		bookService.saveBook(book);
		return "Book added Successfully!";
	}

	@Operation(summary = "Get book by ID", description = "Fetch a single book using its ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Book found"),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	@GetMapping("/getBook/{id}")
	public Book getBookById(@PathVariable int id) {
		return bookService.getBookById(id);

	}

	@Operation(summary = "Get all books", description = "Fetch all books from the database")
	@GetMapping("/getBooks")
	public List<Book> getAllBooks() {
		return bookService.findAllBooks();
	}

	@Operation(summary = "Delete book", description = "Deletes a book using its ID")
	@DeleteMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable int id) {

		bookService.deleteBook(id);
		return "Book deleted Successfully";
	}

	@Operation(summary = "Update book", description = "Updates all details of a book")
	@PutMapping("/updateBook/{id}")
	public Book updateBook(@PathVariable int id, @RequestBody Book book) {

		return bookService.updateBook(id, book);

	}

	@Operation(summary = "Edit book", description = "Partially updates book details")
	@PatchMapping("/editBook/{id}")
	public Book editBook(@PathVariable int id, @RequestBody Book book) {

		return bookService.editBookById(id, book);

	}

	@Operation(summary = "Get books with pagination")
	@GetMapping("/getBooks/page")
	public List<Book> getBooksByPagination(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize) {
		List<Book> bookList = bookService.getBooksByPagination(pageNo, pageSize);

		return bookList;
	}

	// sorting
	@Operation(summary = "Sort books by price", description = "Sort books by price (asc/desc)")
	@GetMapping("/getBookSortByPrice")
	public List<Book> getBooksSortByPrice(@RequestParam(defaultValue = "asc") String direction) {
		return bookService.getBooksSortByPrice(direction);
	}

	// searching
	@Operation(summary = "Search book by name")
	@GetMapping("/getBookBySearch/{name}")
	public List<Book> getBookByName(@PathVariable String name) {
		return bookService.getBookByName(name);
	}

	// filtering data acc to price and author name
	@GetMapping("/getBook/filter")
	@Operation(summary = "Filter books", description = "Filter books by author and price range")
	public List<Book> getBookByFilter(@RequestParam(required = false) String author,
			@RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {

		Map<String, String> filters = new HashMap<String, String>();
		if (author != null && !author.isEmpty()) {
			filters.put("author", author);
		}
		if (minPrice != null) {
			filters.put("minPrice", String.valueOf(minPrice));
		}
		if (maxPrice != null) {
			filters.put("maxPrice", String.valueOf(maxPrice));
		}
		return bookService.getBooksByFilter(filters);

	}
}
