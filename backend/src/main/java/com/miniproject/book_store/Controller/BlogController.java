package com.miniproject.book_store.Controller;

import com.miniproject.book_store.Entity.Blogs;
import com.miniproject.book_store.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping("/create/{uid}")
    public Blogs createBlog(@RequestBody Blogs blog, @PathVariable Integer uid){
        return blogService.createBlog(blog,uid);
    }

    @PutMapping("/update/{bid}/{uid}")
    public Blogs updateBlog(@RequestBody Blogs blog, @PathVariable Integer bid, @PathVariable Integer uid){
        return blogService.updateBlog(blog,bid,uid);
    }

    @DeleteMapping("/delete/{bid}/{uid}")
//    public void deleteBlog(@PathVariable Integer bid, @PathVariable Integer uid){
//        blogService.deleteBlog(bid,uid);
//    }

    public ResponseEntity<?> deleteBlog(@PathVariable Integer bid, @PathVariable Integer uid){
        try {
            blogService.deleteBlog(bid, uid);
            return ResponseEntity.ok("Blog deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }


    @GetMapping("/get/{bid}")
    public ResponseEntity<?> getBlogById(@PathVariable Integer bid){
        Blogs blog = blogService.getBlogById(bid);
        if(blog!=null){
            return ResponseEntity.ok(blog);
        }
        else{
            return ResponseEntity.status(404).body("blog not found");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBlogs(){
        List<Blogs> blogs= blogService.getAllBlogs();
//        if(blogs!=null){
            return ResponseEntity.ok(blogs);
//        }
//        else{
//            return ResponseEntity.status(404).body("Blogs not found");
//        }
    }
}

