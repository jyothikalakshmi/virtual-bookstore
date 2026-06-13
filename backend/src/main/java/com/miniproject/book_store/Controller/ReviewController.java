package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.Reviews;
import com.miniproject.book_store.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/add/{uid}/{bid}")
    public ResponseEntity<?> addReview(@RequestBody Reviews review, @PathVariable Integer uid, @PathVariable Integer bid){
        try {
            Reviews r=reviewService.addReview(review,uid, bid);
            return ResponseEntity.ok(r);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/update/{uid}/{rid}")
    public ResponseEntity<?> updateReview(@RequestBody Reviews review, @PathVariable Integer uid, @PathVariable Integer rid){
       try {
           Reviews r = reviewService.updateReview(review, uid, rid);
           return ResponseEntity.ok(r);
       } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
       }
    }

    @DeleteMapping("/delete/{rid}")
    public void DeleteReview(@PathVariable Integer rid){
        reviewService.deleteReview(rid);
    }

    @GetMapping("/getByBook/{bid}")
    public ResponseEntity<?> getReviewByBook(@PathVariable Integer bid){
        List<Reviews> reviews = reviewService.getReviewsByBook(bid);
//        if(!reviews.isEmpty()){
            return ResponseEntity.ok(reviews);
//        }
//        else{
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reviews not found");
//        }
    }
    @GetMapping("/getByUser/{uid}")
    public ResponseEntity<?> getReviewByUser(@PathVariable Integer uid){
        List<Reviews> reviews = reviewService.getReviewsByBook(uid);
        if(!reviews.isEmpty()){
            return ResponseEntity.ok(reviews);
        }
        else{
            return ResponseEntity.status(404).body("Reviews not found");
        }
    }

}
