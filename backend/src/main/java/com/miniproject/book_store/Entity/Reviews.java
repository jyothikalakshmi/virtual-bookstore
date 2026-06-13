package com.miniproject.book_store.Entity;

import jakarta.persistence.*;


@Entity
@Table(name="reviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","book_id"})
})
public class Reviews {

    @Id
    @Column(name="review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer review_id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private BookEntity book_id;

    private int rating;
    private String review_text;

    public Reviews(Integer review_id, User user_id, BookEntity book_id, int rating, String review_text) {
        this.review_id = review_id;
        this.user_id=user_id;
        this.book_id=book_id;
        this.rating=rating;
        this.review_text=review_text;
    }
    public Reviews(){}

    public Integer getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public BookEntity getBook_id() {
        return book_id;
    }

    public void setBook_id(BookEntity book_id) {
        this.book_id = book_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }
}

