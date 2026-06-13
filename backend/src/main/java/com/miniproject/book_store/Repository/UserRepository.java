package com.miniproject.book_store.Repository;

import com.miniproject.book_store.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// by extending, gives ready made methods(save, findall, delete....) no need to write sql

//public class UserRepository{}
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);

}
