package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.Book;
import com.BookStoreBE.utilityClasses.GENRE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book,Integer> {



    @Query("SELECT b FROM Book b ORDER BY b.qtySold DESC,b.rating DESC LIMIT :limit")
    List<Book> findTopSellingBooksWithLimit(@Param("limit") Integer limit);



    @Query("SELECT b FROM Book b WHERE b.genre=:genre ORDER BY b.qtySold DESC LIMIT :limit")
    List<Book> findAllByGenre(@Param("genre") GENRE genre, @Param("limit") Integer limit);


    @Query("SELECT b FROM Book b ORDER BY b.discount DESC,b.qtySold DESC LIMIT :limit")
    List<Book> findBooksWithMostDiscount(@Param("limit") Integer limit);

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC,b.qtySold DESC LIMIT :limit")
    List<Book> findTopRatedBooks(@Param("limit") Integer limit);

    @Query("SELECT b FROM Book b ORDER BY b.createdAt DESC,b.qtySold DESC LIMIT :limit")
    List<Book> findNewArrivals(@Param("limit") Integer limit);
}
