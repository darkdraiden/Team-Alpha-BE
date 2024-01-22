package com.BookStoreBE.Service;


import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    public ApiResponse<List<Book>> getALlBooks(){

        List<Book> allBook = bookRepository.findAll();

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully.",
                allBook
        );
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }

}
