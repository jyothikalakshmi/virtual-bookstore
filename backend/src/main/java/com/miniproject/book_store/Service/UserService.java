package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user){
        return  userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        // Simple check (In production, use BCrypt to match hashed passwords!)
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }
}
