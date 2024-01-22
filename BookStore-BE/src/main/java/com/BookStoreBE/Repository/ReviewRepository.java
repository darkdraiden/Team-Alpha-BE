package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Optional<List<Review>> findAllByBookId(Integer bookId);
}
