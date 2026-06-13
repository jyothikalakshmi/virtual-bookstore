import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component,OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../components/navbar/navbar';
import { Auth } from '../../services/auth';
import { Blog } from '../../services/blog';

@Component({
  selector: 'app-blogs',
  imports: [CommonModule,FormsModule,Navbar],
  templateUrl: './blogs.html',
  styleUrl: './blogs.css',
})
export class Blogs {
  blogs:any[]=[];
  userId:number=0;
  // userRole:string='';
  isAdmin=false;

  showForm=false;
  isEditing=false;
  editBlogId:number=0;

  newBlog={title:'',content:''};
    successMsg='';
   errorMsg='';

   constructor(private blogService:Blog,private auth:Auth,private cdr:ChangeDetectorRef){}

    ngOnInit() {
    this.userId = this.auth.getUserId();
    // this.userRole = this.auth.getRole();
    this.isAdmin=this.auth.isAdmin();
    this.loadBlogs();
  }
 
  loadBlogs() {
    this.blogService.getAllBlogs().subscribe({
      next: (data) => {
        this.blogs = data;
        this.cdr.detectChanges();
      },
      error: () => this.errorMsg = 'Failed to load blogs.'
    });
  }

   toggleForm() {
    this.showForm = !this.showForm;
    this.isEditing = false;
    this.newBlog = { title: '', content: '' };
    this.successMsg = '';
    this.errorMsg = '';
  }
 
  submitBlog() {
    const data = {
      // user_id: this.userId,
      title: this.newBlog.title,
      content: this.newBlog.content
    };

     if (this.isEditing) {
      this.blogService.updateBlog(this.editBlogId,this.userId, data).subscribe({
        next: () => {
          this.successMsg = 'Blog updated successfully!';
          this.showForm = false;
          this.loadBlogs();
        },
        error: () => this.errorMsg = 'Failed to update blog.'
      });
    } else {
      this.blogService.createBlog(this.userId,data).subscribe({
        next: () => {
          this.successMsg = 'Blog posted successfully!';
          this.showForm = false;
          this.loadBlogs();
        },
        error: () => this.errorMsg = 'Failed to create blog.'
      });
    }
  }
 
  editBlog(blog: any) {
    this.isEditing = true;
    this.editBlogId = blog.blog_id;
    this.newBlog = { title: blog.title, content: blog.content };
    this.showForm = true;
    window.scrollTo(0,0);
  }
 
  deleteBlog(blogId: number) {
    if (confirm('Are you sure you want to delete this blog?')) {
      this.blogService.deleteBlog(blogId,this.userId).subscribe({
        next: () =>{
          this.successMsg='Blog deleted';
          //  this.loadBlogs();
          this.blogs=this.blogs.filter(blog=>blog.blog_id != blogId);
        } ,
        error: () => this.errorMsg = 'Failed to delete blog.'
      });
    }
  }
 
  canEditOrDelete(blog: any): boolean {
    return blog.user_id === this.userId || this.isAdmin;
  }
}

