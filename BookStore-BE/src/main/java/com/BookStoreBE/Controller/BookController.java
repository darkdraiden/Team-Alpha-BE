package com.BookStoreBE.Controller;


import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Service.BookService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import jdk.jshell.Snippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/book")
public class BookController {

    @Autowired
    BookService bookService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(){
        // get all books
        ApiResponse<List<Book>> allBooks=bookService.getALlBooks();

        return new ResponseEntity<>(allBooks, HttpStatusCode.valueOf(allBooks.getStatusCode()));
    }


    // will add Autherization later so only admin can add book
    @PostMapping
    public void addBook(@RequestBody Book book){
        bookService.addBook(book);
    }
}
