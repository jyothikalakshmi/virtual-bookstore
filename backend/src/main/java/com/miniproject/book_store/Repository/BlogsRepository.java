package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blogs,Integer> {

}
