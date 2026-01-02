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

@RestController
@RequestMapping("/api")
public class BookController {
	private BookService bookService;
	@Autowired
	public void setBookService(BookService bookService) {
		this.bookService=bookService;
	}
	
	//add data in database
	@PostMapping("/addBook")
	public String addBook(@RequestBody  Book book ) {
	    bookService.saveBook(book);
	    return "Book added Successfully!";
	}
	
	//fetch data from db
	@GetMapping("/getBook/{id}")
	public Book getBookById(@PathVariable int id) {
		return bookService.getBookById(id);
		
	}
	
	@GetMapping("/getBooks")
	public List<Book> getAllBooks(){
	return	bookService.findAllBooks();
	}
	
	
	@DeleteMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable int id) {
		 
		bookService.deleteBook(id);
		return "Book deleted Successfully";
	}
	
	@PutMapping("/updateBook/{id}")
	public Book updateBook(@PathVariable int id,@RequestBody Book book) {
		
		return bookService.updateBook(id,book);
		
	}
	
	  
	@PatchMapping("/editBook/{id}")
	public Book editBook(@PathVariable int id,@RequestBody Book book) {
		
		return bookService.editBookById(id,book);
		
	}
	
	//pagination
	@GetMapping("/getBooks/page")
	public List<Book> getBooksByPagination(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize) {
		List<Book> bookList = bookService.getBooksByPagination(pageNo, pageSize);

		return bookList;
	}
	//sorting
	@GetMapping("/getBookSortByPrice")
	public List<Book> getBooksSortByPrice(@RequestParam (defaultValue = "asc")String direction){
		return bookService.getBooksSortByPrice(direction);
	}
	
	//searching
	@GetMapping("/getBookBySearch/{name}")
    public List<Book> getBookByName(@PathVariable String name){
		return bookService.getBookByName(name);
	}
	
	//filtering data acc to price and author name
	@GetMapping("/getBook/filter")
	public List<Book> getBookByFilter(@RequestParam(required = false) String author,@RequestParam (required = false) Double minPrice,@RequestParam(required = false)  Double maxPrice){
		
		Map<String, String> filters=new HashMap<String, String>();
		if(author !=null && ! author.isEmpty()) {
		filters.put("author", author);
		}
		if(minPrice!=null) {
			filters.put("minPrice",String.valueOf(minPrice));
		}
		if(maxPrice!=null) {
			filters.put("maxPrice",String.valueOf(maxPrice));
		}
		return bookService.getBooksByFilter(filters);
		
	}
}
