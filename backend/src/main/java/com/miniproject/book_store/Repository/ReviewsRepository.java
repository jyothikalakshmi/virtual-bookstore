package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    @Query("SELECT r FROM Reviews r WHERE r.book_id.book_id = :bid")
    List<Reviews> findByBook_id(@Param("bid") Integer bid);

    @Query("SELECT r FROM Reviews r WHERE r.user_id.userId = :uid")
    List<Reviews> findByUser_id(@Param("uid") Integer uid);
}
