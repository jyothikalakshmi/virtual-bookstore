package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
   List<BookEntity> findByAuthor(String author);
   List<BookEntity> findByCategory(String category);
   List<BookEntity> findByBname(String bname);
}
