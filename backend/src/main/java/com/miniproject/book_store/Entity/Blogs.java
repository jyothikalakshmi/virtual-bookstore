package com.miniproject.book_store.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="blogs")
public class Blogs {
    @Id
    @Column(name="blog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blog_id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user_id;

    private String title;
    private String content;

    public Blogs(Integer blog_id, User user_id, String title, String content) {
        this.blog_id = blog_id;
        this.user_id=user_id;
        this.title=title;
        this.content=content;
    }
    public Blogs(){}

    public Integer getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}