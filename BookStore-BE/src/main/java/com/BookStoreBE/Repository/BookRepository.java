package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.Book;
import com.BookStoreBE.utilityClasses.GENRE;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository  extends JpaRepository<Book,Integer> {



    @Query("SELECT b FROM Book b ORDER BY b.qtySold DESC,b.rating DESC")
    List<Book> findTopSellingBooksWithLimit(Pageable pageable);



    @Query("SELECT b FROM Book b WHERE b.genre=:genre ORDER BY b.qtySold DESC ")
    List<Book> findAllByGenre(@Param("genre") GENRE genre, Pageable pageable);


    @Query("SELECT b FROM Book b ORDER BY b.discount DESC,b.qtySold DESC ")
    List<Book> findBooksWithMostDiscount(Pageable pageable);

    @Query("SELECT b FROM Book b ORDER BY b.rating DESC,b.qtySold DESC ")
    List<Book> findTopRatedBooks(Pageable pageable);

    @Query("SELECT b FROM Book b ORDER BY b.createdAt DESC,b.qtySold DESC ")
    List<Book> findNewArrivals(Pageable pageable);
}
