package com.BookStoreBE.Repository;

import com.BookStoreBE.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Optional<List<Review>> findAllByBookId(Integer bookId);

    @Transactional
    @Modifying
    @Query("update Review set comment=:c where reviewId=:id")
    Integer updateReview(@Param("id") Integer reviewId,@Param("c") String comment);
}
