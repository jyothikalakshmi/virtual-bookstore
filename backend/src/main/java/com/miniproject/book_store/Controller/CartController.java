package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.CartItems;
import com.miniproject.book_store.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
   private CartService cartService;

    @PostMapping("/add/{uid}/{bid}")
    public ResponseEntity<?> addToCart(@PathVariable Integer uid, @PathVariable Integer bid){
        try {
            CartItems saveditem = cartService.addToCart(uid, bid);
            return ResponseEntity.ok(saveditem);
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/getCart/{uid}")
    public ResponseEntity<?> getCart(@PathVariable Integer uid) {
        return ResponseEntity.ok(cartService.getCartByUser(uid));
    }

    @PutMapping("/update/{itemId}/{qty}")
    public ResponseEntity<?> updateQty(@PathVariable Integer itemId, @PathVariable int qty) {
        return ResponseEntity.ok(cartService.updateQuantity(itemId, qty));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> removeItem(@PathVariable Integer itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.ok("Item removed");
    }

    @DeleteMapping("/clear/{uid}")
    public ResponseEntity<?> clearCart(@PathVariable Integer uid) {
        cartService.clearCart(uid);
        return ResponseEntity.ok("Cart cleared");
    }

}
