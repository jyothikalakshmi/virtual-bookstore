package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.OrderItems;
import com.miniproject.book_store.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
//    List<OrderItems> findByOrder_id(Orders order_id);

    @Query("SELECT oi FROM OrderItems oi WHERE oi.order_id = :order")
    List<OrderItems> findByOrder_Id(@Param("order") Orders order);

//    @Query("SELECT COUNT(oi) > 0 FROM OrderItems oi"+" WHERE oi.order_id.user_id.userId = :uid"+" AND oi.book_id.book_id= :bid")
//    boolean existsByUserAndBook(@Param("uid") Integer uid, @Param("bid") Integer bid);

    @Query("SELECT CASE WHEN COUNT(oi) > 0 THEN true ELSE false END FROM OrderItems oi WHERE oi.order_id.user_id.userId = :uid AND oi.book_id.book_id= :bid")
    boolean existsByUserAndBook(@Param("uid") Integer uid, @Param("bid") Integer bid);

}
