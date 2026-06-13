package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.Orders;
import com.miniproject.book_store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
//    List<Orders> findByUser_id(User user_id);

//    @Query("SELECT o FROM Orders o WHERE o.user_id = :user")
//    List<Orders> findByUser_Id(@Param("user") User user);

    @Query("SELECT o FROM Orders o WHERE o.user_id.userId = :uid")
    List<Orders> findByUser_Id(@Param("uid") Integer uid);
}
