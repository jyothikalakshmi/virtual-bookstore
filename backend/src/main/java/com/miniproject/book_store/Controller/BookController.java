package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.BookEntity;
import com.miniproject.book_store.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add/{id}")
//    public BookEntity addBooks(@RequestBody BookEntity book, @PathVariable Integer id){
//
////        return bookService.addBooks(book,id);
//    }
    public ResponseEntity<?> addBooks(@RequestBody BookEntity book, @PathVariable Integer id){
        try{
        BookEntity b= bookService.addBooks(book,id);
            return ResponseEntity.ok(b);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{bookId}/by/{userId}")
    public ResponseEntity<?> DeleteById(@PathVariable Integer bookId, @PathVariable Integer userId){
        try {
            bookService.deleteById(bookId, userId);
            return ResponseEntity.ok("book deleted successfully");
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/allBooks")
    public ResponseEntity<?> getAllBooks(){
        List<BookEntity> books = bookService.getAllBooks();
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(404).body ("books not found.");
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> GetBookById(@PathVariable Integer bookId) {
        BookEntity b = bookService.getBooksById(bookId);
        if (b != null) {
            return ResponseEntity.ok(b);
        } else {
            return ResponseEntity.status(404).body("book not found with id " + bookId);
        }

    }

    @GetMapping("/author/{author}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable String author){
        List<BookEntity> books = bookService.getBooksByAuthor(author);
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(400).body("Books not found");
        }
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getBooksByCategory(@PathVariable String category){
        List<BookEntity> books = bookService.getBooksByCategory(category);
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(400).body("Books not found");
        }
    }
    @GetMapping("/title/{bname}")
    public ResponseEntity<?> getBooksByTitle(@PathVariable String bname){
        List<BookEntity> books= bookService.getBooksByTitle(bname);
        if(!books.isEmpty()){
            return ResponseEntity.ok(books);
        }
        else{
            return ResponseEntity.status(400).body("Books not found");
        }
    }
    @PostMapping("/update/{bookId}/by/{userId}")
    public ResponseEntity<?> updateBook(@RequestBody BookEntity book, @PathVariable Integer bookId, @PathVariable Integer userId){
        try{
            BookEntity b=bookService.updateBook(book, bookId,userId);
            return ResponseEntity.ok(b);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
