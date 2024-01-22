package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book,Integer> {

}
