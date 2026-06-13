package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.Cart;
import com.miniproject.book_store.Entity.CartItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems,Integer> {
//    Optional<CartItems> findByCartAndBook(Cart cart_id, BookEntity book_id);

    @Query("SELECT ci FROM CartItems ci WHERE ci.cart_id = :cart AND ci.book_id = :book")
    Optional<CartItems> findByCartAndBook(@Param("cart") Cart cart, @Param("book") BookEntity book);

//    List<CartItems> findByCart_id(Cart cart_id);
//
//    // To clear the cart (needs @Modifying if using a custom query)
//    void deleteByCart_id(Cart cart_id);


    @Query("SELECT ci FROM CartItems ci WHERE ci.cart_id = :cart")
    List<CartItems> findByCart_id(@Param("cart") Cart cart);

    // 3. Clear all items from a specific cart (Used for Clear Cart logic)
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItems ci WHERE ci.cart_id = :cart")
    void deleteByCart_id(@Param("cart") Cart cart);


    // Your existing add to cart logic...
//    Optional<CartItems> findByCart_idAndBook_id(Cart cart_id, BookEntity book_id);
}


