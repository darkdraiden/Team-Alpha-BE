package com.BookStoreBE.Controller;


import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Service.BookService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.GENRE;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path="/api/v1/book")
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getALlBooks();

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Integer bookId){
        ApiResponse<Book> res=bookService.getBookById(bookId);

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getStatusCode()));
    }

    @GetMapping("/top-selling")
    public ResponseEntity<ApiResponse<List<Book>>> getTopSellingBooks(
            @RequestParam(name="limit",required = false,defaultValue = "100") int limit
    ){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getTopSellingBooks(limit);

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }

    @GetMapping("/by-genre")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByGenre(
            @RequestParam(name="genre",required = false,defaultValue = "NARRATIVE") GENRE genre,
            @RequestParam(name="limit",required = false,defaultValue = "100") int limit
    ){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getBooksByGenre(genre,limit);

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }

    @GetMapping("/hot-deals")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksWithMostDiscount(
            @RequestParam(name="limit",required = false,defaultValue = "100") int limit
    ){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getBooksWithMostDiscount(limit);

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponse<List<Book>>> getTopRatedBooks(
            @RequestParam(name="limit",required = false,defaultValue = "100") int limit
    ){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getTopRatedBooks(limit);

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }

    @GetMapping("/new-arrivals")
    public ResponseEntity<ApiResponse<List<Book>>> getNewArrivalsBooks(
            @RequestParam(name="limit",required = false,defaultValue = "100") int limit
    ){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getNewArrivalsBooks(limit);

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }


    // will add Autherization later so only admin can add book
    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }
}
