package com.miniproject.book_store.Entity;

import jakarta.persistence.*;
import org.hibernate.query.Order;
import org.springframework.data.repository.cdi.Eager;

@Entity
@Table(name="order_items")
public class OrderItems {
    @Id
    @Column(name="order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_item_id;
    private int quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders order_id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private BookEntity book_id;

    public OrderItems(Integer order_item_id, int quantity, Orders order_id, BookEntity book_id, Double price) {
        this.order_item_id = order_item_id;
        this.quantity=quantity;
        this.order_id=order_id;
        this.book_id=book_id;
        this.price=price;
    }
    public OrderItems(){}

    public Integer getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(int order_item_id) {
        this.order_item_id = order_item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Orders order_id) {
        this.order_id = order_id;
    }

    public BookEntity getBook_id() {
        return book_id;
    }

    public void setBook_id(BookEntity book_id) {
        this.book_id = book_id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

