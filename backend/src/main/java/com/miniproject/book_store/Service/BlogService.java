package com.miniproject.book_store.Service;

import com.miniproject.book_store.Entity.Blogs;
import com.miniproject.book_store.Entity.User;
import com.miniproject.book_store.Repository.BlogsRepository;
import com.miniproject.book_store.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService  {
     @Autowired
    private BlogsRepository blogsRepository;
     @Autowired
    private UserRepository userRepository;

     public Blogs createBlog(Blogs blog, Integer uid){

         User user = userRepository.findById(uid).orElseThrow(()->new RuntimeException("user not found"));
         blog.setUser_id(user);

         return blogsRepository.save(blog);
     }

     public Blogs updateBlog(Blogs blog, Integer bid, Integer uid){
//         User user= userRepository.findById(uid).orElseThrow(()->new RuntimeException("user not found"));
         Blogs existing = blogsRepository.findById(bid).orElseThrow(()->new RuntimeException("blog not found"));
//         if(!existing.getUser_id().equals(uid)){
//             throw new RuntimeException("You are not authorised to edit this blog");
//         }
         Integer ownerId = existing.getUser_id().getUserId();

         if (!ownerId.equals(uid)) {
             throw new RuntimeException("You are not authorised to edit this blog");
         }

         if(blog.getTitle()!=null) existing.setTitle(blog.getTitle());
         if(blog.getContent()!=null) existing.setContent(blog.getContent());

         return blogsRepository.save(existing);
     }

     public void deleteBlog(Integer bid, Integer uid){
         Blogs blog= blogsRepository.findById(bid).orElseThrow(()->new RuntimeException("blog not found"));

         Integer ownerid= blog.getUser_id().getUserId();
         if(!ownerid.equals(uid)){
             throw new RuntimeException("You are not allowed to delete this blog");
         }
         else{
             blogsRepository.deleteById(bid);
         }
     }

     public Blogs getBlogById(Integer bid){
         return blogsRepository.findById(bid).orElseThrow(()->new RuntimeException("blog not found"));
     }

     public List<Blogs> getAllBlogs(){
         return blogsRepository.findAll();
     }


}
