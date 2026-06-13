package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.Reviews;
import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Repository.BookRepository;
import com.miniproject.book_store.Repository.OrderItemsRepository;
import com.miniproject.book_store.Repository.ReviewsRepository;
import com.miniproject.book_store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public Reviews addReview(Reviews review, Integer uid, Integer bid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new RuntimeException("user not found"));
        BookEntity book = bookRepository.findById(bid).orElseThrow(() -> new RuntimeException("book not found"));
        boolean hasPurchased = orderItemsRepository.existsByUserAndBook(uid, bid);

        System.out.println("user id:" +uid);
        System.out.println("Book id:"+bid);
        System.out.println("has purchased: "+ hasPurchased);

        if (!hasPurchased) {
            throw new RuntimeException("You can only review books you purchased");
        }
        review.setUser_id(user);
        review.setBook_id(book);
        return reviewsRepository.save(review);
    }

    public Reviews updateReview(Reviews review, Integer uid, Integer rid){
        Reviews existing = reviewsRepository.findById(rid).orElseThrow(()->new RuntimeException("review not found"));
        if(!existing.getUser_id().getUserId().equals(uid)){
            throw new RuntimeException("you are not authorized to edit this review");
        }

        if(review.getReview_text()!=null) existing.setReview_text(review.getReview_text());
        if(review.getRating()!=0) existing.setRating(review.getRating());

        return reviewsRepository.save(existing);
    }

    public void deleteReview(Integer rid){
        reviewsRepository.deleteById(rid);
    }

    public List<Reviews> getReviewsByBook(Integer bid){
        BookEntity book= bookRepository.findById(bid).orElseThrow(()->new RuntimeException("book not found"));
        return reviewsRepository.findByBook_id(bid);
    }

    public List<Reviews> getReviewByUser(Integer uid){
//        User user= userRepository.findById(uid).orElseThrow(()->new RuntimeException("user not found"));
        return reviewsRepository.findByUser_id(uid);
    }

}
