
import { Component,OnInit,ChangeDetectorRef } from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../components/navbar/navbar';
import {Router} from '@angular/router';
import { Book } from '../../services/book';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-books',
  imports: [CommonModule,FormsModule,Navbar],
  templateUrl: './books.html',
  styleUrl: './books.css',
})
export class Books {

  allBooks:any[]=[];
  filteredBooks:any[]=[];
  pagedBooks:any[]=[];

  searchTerm='';
  selectedCategory='';
  selectedSort='';
  categories:string[]=[];

  currentPage=1;
  pageSize=8;
  totalPages=1;

  isAdmin=false;
  userId:number=0;

  // for admin add/edit form
  showForm=false;
  isEditing=false;
  editBookId:number=0;
  newBook={bname:'',author:'',price:0,description:'',category:'',stock:0};
  formError='';
  formSuccess='';

  constructor(private bookservice:Book, private router:Router, private auth:Auth,private cdr:ChangeDetectorRef){}

  ngOnInit(){
    this.userId=this.auth.getUserId();
    this.isAdmin=this.auth.isAdmin();
    this.loadBooks();
  }
  loadBooks(){
    this.bookservice.getAllBooks().subscribe({
      next:(data)=>{
        this.allBooks=data;
        this.filteredBooks=data;
        this.categories=[...new Set(data.map((b:any)=>b.category))];
        this.applyFilters();
        this.cdr.detectChanges();
      },
      error:()=>{
        console.log("Error loading books");
      }
    });
  }
  
  applyFilters(){
    let result=[...this.allBooks];

    if(this.searchTerm.trim()){
      const term= this.searchTerm.toLowerCase();
      result=result.filter(b=>
        b.bname?.toLowerCase().includes(term)|| b.author?.toLowerCase().includes(term)
      );
    }

    if(this.selectedCategory){
      result=result.filter(b=>b.category===this.selectedCategory);
    }

    if(this.selectedSort === 'title_asc'){
      result.sort((a,b)=>a.bname.localeCompare(b.bname));
    }
    else if(this.selectedSort === 'title_desc'){
      result.sort((a,b)=>b.bname.localCompare(a.bname));
    }
    else if(this.selectedSort === 'price_asc'){
      result.sort((a,b)=>a.price-b.price);
    }
    else if(this.selectedSort === 'price_desc'){
      result.sort((a,b)=>b.price-a.price);
    }

    this.filteredBooks=result;
    this.currentPage=1;
    this.updatePagination();

  }

  updatePagination(){
    this.totalPages=Math.ceil(this.filteredBooks.length/this.pageSize);
    const start=(this.currentPage-1)* this.pageSize;
    this.pagedBooks=this.filteredBooks.slice(start,start+this.pageSize);
  }

  goToPage(page:number){
    if(page<1 || page>this.totalPages) return;
    this.currentPage=page;
    this.updatePagination();
  }
  
  getPages():number[]{
    return Array.from({length:this.totalPages},(_,i)=>i+1);
  }
  viewBook(id:number){
    this.router.navigate(['/books',id]);
  }

  // admin functions
  toggleForm(){
    this.showForm=!this.showForm;
    this.isEditing=false;
    this.newBook={bname:'',author:'',price:0,description:'',category:'',stock:0};
    this.formError='';
    this.formSuccess='';
  }
  editBook(book:any){
    this.isEditing=true;
    this.editBookId=book.book_id;
    this.newBook={
      bname:book.bname,
      author:book.author,
      price:book.price,
      description:book.description,
      category:book.category,
      stock:book.stock
    };
    this.showForm=true;
    window.scrollTo(0,0);
  }
  submitBook(){
    if(this.isEditing){
      this.bookservice.updateBook(this.editBookId,this.userId,this.newBook).subscribe({
        next:()=>{
          this.formSuccess='Book updated successfully';
          this.showForm=false;
          this.loadBooks();
        },
        error:()=>this.formError='Failed to update book.'
      });
    }
    else{
      this.bookservice.addBook(this.userId,this.newBook).subscribe({
        next:()=>{
          this.formSuccess='Book added successfully!';
          this.showForm=false;
          this.loadBooks();
        },
        error:()=>this.formError='Failed to add book.'
      });
    }
  }

  deleteBook(bookId:number){
    if(confirm('Are you sure you want to delete this book?')){
      this.bookservice.deleteBook(bookId,this.userId).subscribe({
        next:()=>{
          console.log("delete success, bookId: ",bookId);
          console.log("before filter:",this.allBooks.length);
          this.formSuccess='Book deleted successfully!';
          this.allBooks=this.allBooks.filter(b=>b.book_id!==bookId);
          console.log("after filter:",this.allBooks.length);
          this.filteredBooks= this.filteredBooks.filter(b=>b.book_id !== bookId);
          // this.updatePagination();
          // this.cdr.detectChanges();
          this.loadBooks();
          // this.applyFilters();
        },
        error:()=>this.formError='Failed to delete book.'
      });
    }
  }



}
