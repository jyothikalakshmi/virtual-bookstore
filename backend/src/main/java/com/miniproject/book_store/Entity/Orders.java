package com.miniproject.book_store.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
public class Orders {

     @Id
     @Column(name="order_id")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;

    private LocalDateTime order_date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

    private Double totalPrice;

    public Orders(Integer order_id, LocalDateTime order_date, User user_id, Double totalPrice) {
        this.order_id=order_id;
        this.order_date = order_date;
        this.user_id=user_id;
        this.totalPrice=totalPrice;
    }
    public Orders(){}

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
