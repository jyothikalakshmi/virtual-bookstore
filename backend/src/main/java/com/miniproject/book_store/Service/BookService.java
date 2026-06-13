package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Repository.BookRepository;
import com.miniproject.book_store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;


    public BookEntity addBooks(BookEntity book , Integer id){
        User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        String role=user.getRole();
        if(role.equals("admin")){
        return bookRepository.save(book);
    }
        else{
            throw new RuntimeException("Access denied. Only Admins can add books");
        }
   }

   public void deleteById(Integer bookId,Integer userId){
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        if(user.getRole().equals("admin")){
         bookRepository.deleteById(bookId);}
        else{
            throw new RuntimeException(("Access denied. Only Admins can delete books"));
        }
   }

   public BookEntity getBooksById(Integer bookId){
        return bookRepository.findById(bookId).orElse(null);

   }

   public List<BookEntity> getAllBooks(){
        return bookRepository.findAll();
   }

   public List<BookEntity> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author);
   }
    public List<BookEntity> getBooksByCategory(String category){
        return bookRepository.findByCategory(category);
    }
    public List<BookEntity> getBooksByTitle(String bname){
//        return bookRepository.findByTitle(bname)
//       this doesnt work bcz title is not there in books entity , so findbyTitle wont work.
        return bookRepository.findByBname(bname);
    }

    public BookEntity updateBook(BookEntity book, Integer bid,Integer uid ){
        User user= userRepository.findById(uid).orElseThrow(()->new RuntimeException(("user not found")));
        if(user.getRole().equals("admin")){
            BookEntity existing= bookRepository.findById(bid).orElseThrow(()->new RuntimeException("book not found"));
            if(existing!=null){
                if(book.getBname()!=null)existing.setBname(book.getBname());
               if(book.getAuthor()!=null) existing.setAuthor(book.getAuthor());
                if(book.getPrice()!=null)existing.setPrice(book.getPrice());
               if(book.getDescription()!=null) existing.setDescription(book.getDescription());
               if(book.getCategory()!=null) existing.setCategory(book.getCategory());
               if(book.getStock()!=0) existing.setStock(book.getStock());
            }
            return bookRepository.save(existing);
        }
        else{
            throw new RuntimeException("Access denied. only admins can edit");
        }
    }
}