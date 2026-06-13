package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.OrderItems;
import com.miniproject.book_store.Entity.Orders;
import com.miniproject.book_store.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{uid}")
    public ResponseEntity<?> placeOrder(@PathVariable Integer uid) {
        try {
            Orders order = orderService.placeOrder(uid);
            if (order != null) {
                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order not placed successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getOrders/{uid}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Integer uid){
        List<Orders> orders= orderService.getOrdersByUser(uid);
        if(orders!=null){
            return ResponseEntity.ok(orders);
        }
        else{
            return ResponseEntity.status(404).body("No orders found");
        }
    }

    @GetMapping("/orderDetails/{oid}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Integer oid){
        List<OrderItems> orderItems = orderService.getOrderDetails(oid);
        if(orderItems!=null){
            return ResponseEntity.ok(orderItems);
        }
        else {
            return ResponseEntity.status(400).body("order Details not found");
        }
    }
}
