package com.miniproject.book_store.Entity;

import jakarta.persistence.*;

@Entity
public class CartItems {
    @Id
    @Column(name="cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cart_item_id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart_id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private BookEntity book_id;
    private int quantity;

    public CartItems(Integer cart_item_id,Cart cart_id, BookEntity book_id,int qunatity) {
       this.cart_item_id=cart_item_id;
        this.cart_id = cart_id;
        this.book_id=book_id;
        this.quantity=qunatity;
    }
    public CartItems(){}

    public Integer getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public Cart getCart_id() {
        return cart_id;
    }

    public void setCart_id(Cart cart_id) {
        this.cart_id = cart_id;
    }

    public BookEntity getBook_id() {
        return book_id;
    }

    public void setBook_id(BookEntity book_id) {
        this.book_id = book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

