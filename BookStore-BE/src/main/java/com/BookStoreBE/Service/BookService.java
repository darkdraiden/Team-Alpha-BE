package com.BookStoreBE.Service;


import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.GENRE;
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

    public  ApiResponse<List<Book>> getTopSellingBooks(int limit){

        List<Book> topSellingBooks = bookRepository.findTopSellingBooksWithLimit(limit);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "top selling Books fetched successfully.",
                topSellingBooks
        );
    }

    public  ApiResponse<List<Book>> getBooksByGenre(GENRE genre, int limit){

        List<Book> allBooks = bookRepository.findAllByGenre(genre,limit);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully by genre",
                allBooks
        );
    }


    public  ApiResponse<List<Book>> getBooksWithMostDiscount(int limit){

        List<Book> allBooks = bookRepository.findBooksWithMostDiscount(limit);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully with most discount",
                allBooks
        );
    }

    public void addBook(Book book){
        System.out.println(book.toString());
        bookRepository.save(book);
    }

}
