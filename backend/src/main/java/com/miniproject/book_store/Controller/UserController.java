package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }

//    @PostMapping("/login")
//    public User login(@RequestBody User user){
//        return userService.login(user);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            // In a real app, you'd return a JWT token here
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id){
        User user= userService.getUserById(id);
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
