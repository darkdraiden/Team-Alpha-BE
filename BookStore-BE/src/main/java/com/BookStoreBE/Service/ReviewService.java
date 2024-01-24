package com.BookStoreBE.Service;

import com.BookStoreBE.Model.Book;
import com.BookStoreBE.Model.Review;
import com.BookStoreBE.Model.User;
import com.BookStoreBE.Repository.BookRepository;
import com.BookStoreBE.Repository.ReviewRepository;
import com.BookStoreBE.Repository.UserRepository;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;


    public ApiResponse<List<Review>> getAllReviewForABook(Integer bookId){
        Optional<Book> currBook=bookRepository.findById(bookId);

        // if book not found, return 400
        if(!currBook.isPresent()){
            return new ApiResponse<List<Review>>(
                    400,
                    "fail",
                    "book not found with id: "+bookId,
                    null
            );
        }

        Optional<List<Review>> allReview = reviewRepository.findAllByBookId(bookId);

        return new ApiResponse<List<Review>> (
                200,
                "success",
                "fetched successfully",
                allReview.get()
        );
    }

    public ApiResponse<Review> createReview(Review review){
        Integer userId = review.getUserId();
        Integer bookId = review.getBookId();


        // get the user that is trying to post review
        Optional<User> currUser=userRepository.findById(userId);

        // if user not found, return 400
        if(!currUser.isPresent()){
            return new ApiResponse<Review>(
                    400,
                    "fail",
                    "user not found with id: "+userId,
                    null
            );
        }

        // get the book on which user is trying to post review
        Optional<Book> opBook=bookRepository.findById(bookId);

        // if book not found, return 400
        if(!opBook.isPresent()){
            return new ApiResponse<Review>(
                    404,
                    "fail",
                    "book not found with id: "+bookId,
                    null
            );
        }

        if(review.getRating() > 5.0){
            return new ApiResponse<Review>(
                    400,
                    "fail",
                    "rating must be smaller than 5, provided "+review.getRating(),
                    null
            );
        }

        // get books
        Book currBook=opBook.get();

        // calculate new rating
        float prevRating=currBook.getRating();
        int prevCnt=currBook.getReviewCnt();

        float currRating=review.getRating();

        float newRating=(float) ((prevRating*prevCnt)+currRating)/(float)(prevCnt+1);

        // set new rating and review cnt
        currBook.setRating(newRating);
        currBook.setReviewCnt(prevCnt+1);
        bookRepository.save(currBook);

        review.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        // at last post review
        reviewRepository.save(review);

        return new ApiResponse<Review>(
                201,
                "success",
                "review created",
                null
        );
    }

    public ApiResponse<Review> deleteReview(Integer reviewId){
        if(!reviewRepository.findById(reviewId).isPresent()){
            return new ApiResponse<Review>(
                    404,
                    "fail",
                    "review not found!",
                    null
            );
        }
        reviewRepository.deleteById(reviewId);

        return new ApiResponse<Review>(
                204,
                "success",
                "review deleted!",
                null
        );
    }

    public ApiResponse<Review> updateReview(Review updatedReview){
        if(updatedReview.getRating()> 5.0){
            return new ApiResponse<Review>(
                    400,
                    "fail",
                    "Rating must be less than 5, provided "+updatedReview.getRating(),
                    null
            );
        }

        Integer rowsUpdated = reviewRepository.updateReview(
                updatedReview.getReviewId(),
                updatedReview.getComment()
        );

        if(rowsUpdated == 0){
            return new ApiResponse<Review>(
                    404,
                    "fail",
                    "Review not found!",
                    null
            );
        }

        return new ApiResponse<Review>(
                200,
                "success",
                "review updated",
                null
        );
    }


}
