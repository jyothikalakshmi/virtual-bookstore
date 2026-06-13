package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.*;
import com.miniproject.book_store.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private CartRepository cartRepository;

    public Orders placeOrder(Integer uid){
        User user = userRepository.findById(uid).orElseThrow(()->new RuntimeException("user not found"));

        Cart cart= cartRepository.findByUser_Id(user).orElseThrow(()->new RuntimeException("cart not found"));
        System.out.println("cart id:"+cart.getCart_id());

        List<CartItems> cItems= cartItemsRepository.findByCart_id(cart);
        System.out.println("cart items size:"+ cItems.size());
        if(cItems.isEmpty()){
            throw new RuntimeException("Your cart is empty");
        }
        Orders neworder= new Orders();
        neworder.setUser_id(user);
        neworder.setOrder_date(LocalDateTime.now());

        Orders savedorder = orderRepository.save(neworder);

        double total=0;
        for (CartItems item : cItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder_id(savedorder); // Linking to the saved order
            orderItem.setBook_id(item.getBook_id()); // Moving the book reference
            orderItem.setQuantity(item.getQuantity());

            double itemPrice = item.getBook_id().getPrice();
            orderItem.setPrice(itemPrice);

            total+=itemPrice*item.getQuantity();

            orderItemsRepository.save(orderItem);
        }

        savedorder.setTotalPrice(total);
        orderRepository.save(savedorder);

        // 6. Clear the Cart items after successful transfer
        cartItemsRepository.deleteByCart_id(cart);

        return savedorder;
    }

    public List<Orders> getOrdersByUser(Integer uid){
        User user= userRepository.findById(uid).orElseThrow(()->new RuntimeException("User not found"));
        return orderRepository.findByUser_Id(uid);
    }

    public List<OrderItems> getOrderDetails(Integer oid){
        Orders order=orderRepository.findById(oid).orElseThrow(()->new RuntimeException("order not found"));
        return orderItemsRepository.findByOrder_Id(order);
    }


}
