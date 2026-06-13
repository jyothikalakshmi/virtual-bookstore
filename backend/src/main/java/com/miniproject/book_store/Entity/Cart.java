package com.miniproject.book_store.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cart_id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user_id;

    public Cart(Integer cart_id, User user_id) {
        this.cart_id=cart_id;
        this.user_id = user_id;
    }
    public Cart (){

    }

    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
}
