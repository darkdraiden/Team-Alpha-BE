package com.BookStoreBE.Service;


import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.Repository.UserRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import com.BookStoreBE.utilityClasses.GENRE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse<List<Book>> getALlBooks(){

        List<Book> allBook = bookRepository.findAll();

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully.",
                allBook
        );
    }

    public ApiResponse<Book> getBookById(Integer bookId){

        Optional<Book> opBook = bookRepository.findById(bookId);

        if(opBook.isEmpty()){
            return new ApiResponse<Book>(
                    404,
                    "fail",
                    "Book not found!",
                    null
            );
        }

        return new ApiResponse<Book>(
                200,
                "success",
                "Book fetched successfully.",
                opBook.get()
        );
    }

    public  ApiResponse<List<Book>> getTopSellingBooks(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> topSellingBooks = bookRepository.findTopSellingBooksWithLimit(pageable);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "top selling Books fetched successfully.",
                topSellingBooks
        );
    }

    public  ApiResponse<List<Book>> getBooksByGenre(GENRE genre, int limit){
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> allBooks = bookRepository.findAllByGenre(genre,pageable);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully by genre",
                allBooks
        );
    }


    public  ApiResponse<List<Book>> getBooksWithMostDiscount(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> allBooks = bookRepository.findBooksWithMostDiscount(pageable);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Books fetched successfully with most discount",
                allBooks
        );
    }

    public  ApiResponse<List<Book>> getTopRatedBooks(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> allBooks = bookRepository.findTopRatedBooks(pageable);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "top rated Books fetched successfully",
                allBooks
        );
    }

    public  ApiResponse<List<Book>> getNewArrivalsBooks(int limit){
        Pageable pageable = PageRequest.of(0, limit);
        List<Book> allBooks = bookRepository.findNewArrivals(pageable);

        return new ApiResponse<List<Book>>(
                200,
                "success",
                "Newly arrived Books fetched successfully",
                allBooks
        );
    }
    public ApiResponse<String> addBook(Book book,Integer userId){

        Optional<User> opUser=userRepository.findById(userId);

        if(opUser.isEmpty()){
            return new ApiResponse<String>(404,"fail","user not found",null);
        }

        if(opUser.get().getROLE()==null || !opUser.get().getROLE().equals("ADMIN")){
            return new ApiResponse<String>(401,"fail","only admin can add books!",null);
        }

        book.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        book.setReviewCnt(0);
        book.setRating(0);
        bookRepository.save(book);

        return new ApiResponse<String>(200,"success","book added successfully!",null);

    }

    public ApiResponse<String> updateBook(Book book,Integer userId){

        Optional<User> opUser=userRepository.findById(userId);

        if(opUser.isEmpty()){
            return new ApiResponse<String>(404,"fail","user not found",null);
        }

        if(opUser.get().getROLE()==null || !opUser.get().getROLE().equals("ADMIN")){
            return new ApiResponse<String>(401,"fail","only admin can update books!",null);
        }

        Integer bookId=book.getBookId();

        Optional<Book> opBook = bookRepository.findById(bookId);

        if(opBook.isEmpty()){
            return new ApiResponse<String>(404,"fail","book not found",null);
        }

        bookRepository.save(book);

        return new ApiResponse<String>(200,"success","book updated successfully!",null);

    }

}
