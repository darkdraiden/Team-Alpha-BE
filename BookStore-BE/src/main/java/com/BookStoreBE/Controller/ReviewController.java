package com.BookStoreBE.Controller;

import com.BookStoreBE.Model.Review;
import com.BookStoreBE.Service.ReviewService;
import com.BookStoreBE.utilityClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(path="api/v1/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviewForABook(@PathVariable Integer bookId){

        ApiResponse<List<Review>> allReview = reviewService.getAllReviewForABook(bookId);

        return new ResponseEntity<>(allReview, HttpStatusCode.valueOf(allReview.getStatusCode()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> createReview(@RequestBody Review review){


        ApiResponse<Review> res = reviewService.createReview(review);

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getStatusCode()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Review>> deleteReview(@PathVariable Integer reviewId){
        // need to pass userId as well that we need to get from JWT
        ApiResponse<Review> res=reviewService.deleteReview(reviewId);

        return new ResponseEntity<>(res, HttpStatusCode.valueOf(res.getStatusCode()));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Review>> updateReview(@RequestBody Review review){
        ApiResponse<Review> res=reviewService.updateReview(review);

        return new ResponseEntity<>(res,HttpStatusCode.valueOf(res.getStatusCode()));
    }

}
