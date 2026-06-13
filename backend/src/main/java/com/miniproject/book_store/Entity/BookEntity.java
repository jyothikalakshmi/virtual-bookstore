package com.miniproject.book_store.Entity;

import jakarta.persistence.*;

//import lombok.Getter;
//import lombok.Setter;

import java.awt.print.Book;

@Entity
@Table(name="books")
public class BookEntity {

    @Id
    @Column(name="book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer book_id;
    private String bname;
    private String author;
    private Float price;
    private String description;
    private String category;
    private int stock;

    public BookEntity(Integer book_id, String bname, String author, Float price, String description, String category, int stock){
        this.book_id=book_id;
        this.bname=bname;
        this.author=author;
        this.price=price;
        this.description=description;
        this.category=category;
        this.stock=stock;
    }
    public BookEntity(){
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
