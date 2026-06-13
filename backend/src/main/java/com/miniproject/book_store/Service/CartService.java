package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.Cart;
import com.miniproject.book_store.Entity.CartItems;
import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Repository.BookRepository;
import com.miniproject.book_store.Repository.CartItemsRepository;
import com.miniproject.book_store.Repository.CartRepository;
import com.miniproject.book_store.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

//    public CartItems addToCart(Integer uid, Integer bid){
//        User user =userRepository.findById(uid).orElseThrow(()->new RuntimeException("user not found"));
//        BookEntity book=bookRepository.findById(bid).orElseThrow(()-> new RuntimeException("book not found"));

        @Transactional // Important for multistep database operations
        public CartItems addToCart(Integer uid, Integer bid) {
            // 1. Find User and Book
            User user = userRepository.findById(uid)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            BookEntity book = bookRepository.findById(bid)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            // 2. Check if the User already has a Cart. If not, create one.
            Cart cart = cartRepository.findByUser_Id(user)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser_id(user);
                        return cartRepository.save(newCart);
                    });

            // 3. Check if this specific book is already in this specific cart
            // Note: You need this method in your CartItemsRepository
            Optional<CartItems> existingItem = cartItemsRepository.findByCartAndBook(cart, book);

            if (existingItem.isPresent()) {
                // 4. Update quantity if book already exists in cart
                CartItems item = existingItem.get();
                item.setQuantity(item.getQuantity() + 1);
                return cartItemsRepository.save(item);
            } else {
                // 5. Create new CartItem entry if it's a new book for this cart
                CartItems newItem = new CartItems();
                newItem.setCart_id(cart);
                newItem.setBook_id(book);
                newItem.setQuantity(1);
                return cartItemsRepository.save(newItem);
            }
        }

    public List<CartItems> getCartByUser(Integer uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser_Id(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty/not found"));

        return cartItemsRepository.findByCart_id(cart);
    }

    public CartItems updateQuantity(Integer cartItemId, int newQuantity) {
        CartItems item = cartItemsRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        if (newQuantity <= 0) {
            // If they decrease below 1, you might want to remove it entirely
            cartItemsRepository.delete(item);
            return null;
        }

        item.setQuantity(newQuantity);
        return cartItemsRepository.save(item);
    }

    public void removeFromCart(Integer cartItemId) {
        cartItemsRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(Integer uid) {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser_Id(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemsRepository.deleteByCart_id(cart);
    }

    }

