import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component,OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../components/navbar/navbar';
import { ActivatedRoute } from '@angular/router';
import { Book } from '../../services/book';
import { Auth } from '../../services/auth';
import { Review } from '../../services/review';
import { Cart } from '../../services/cart';

@Component({
  selector: 'app-book-detail',
  imports: [CommonModule,FormsModule,Navbar],
  templateUrl: './book-detail.html',
  styleUrl: './book-detail.css',
})
export class BookDetail {

  book:any=null;
  reviews:any[]=[];
  userId:number=0;

  // loaded=false;

  newReview={rating:5, review_text:''};
  reviewSuccess='';
  reviewError='';
  cartMsg='';

  constructor( private route:ActivatedRoute, private bookservice:Book,private reviewservice:Review, 
    private cartservice:Cart, private auth:Auth, private cdr:ChangeDetectorRef){}

  ngOnInit(){
    this.userId=this.auth.getUserId();
    const bookId=Number(this.route.snapshot.paramMap.get('id'));
    this.loadBook(bookId);
    this.loadReviews(bookId);
  }
  loadBook(id:number){
    console.log('loading book with id:',id);
    this.bookservice.getBooksById(id).subscribe({
      next:(data)=>{
        console.log('book data:',data);
        this.book=data;
        // this.loaded=true;
        this.cdr.detectChanges();
      },
      error:(err)=>{
        // this.loaded=true;
        console.log(err);
      }
    });
  }

  loadReviews(id:number){
    this.reviewservice.getReviewByBook(id).subscribe({
      next:(data)=> this.reviews=data,
      error:()=>this.reviews=[]
    });
  }

  addToCart(){
    this.cartMsg='';
    this.cartservice.addToCart(this.userId,this.book.book_id).subscribe({
      next:()=>{
               this.cartMsg='Added to cart successfully!';
               this.cdr.detectChanges();
               alert(this.cartMsg);
      },
      error:()=>{
        this.cartMsg='Failed to add to cart.';
        this.cdr.detectChanges();
      }
      
    });
  }

  submitReview(){
    this.reviewSuccess='';
    this.reviewError='';
    const data={
      rating:this.newReview.rating,
      review_text:this.newReview.review_text
    };
    this.reviewservice.addReview(this.userId,this.book.book_id,data).subscribe({
      next:()=>{
        this.reviewSuccess='Review submitted successfully!';
        this.newReview={rating:5, review_text:''};
        this.cdr.detectChanges();
        this.loadReviews(this.book.book_id);
      },
      error:(err)=>{
        this.reviewError=err?.error?.message|| 'You can only review books you have purchased.';
        this.cdr.detectChanges();
      }
    });
  }

  getStars(rating:number):string{
    return '⭐'.repeat(rating);
  }
  

}