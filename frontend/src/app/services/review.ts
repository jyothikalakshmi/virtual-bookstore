import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Review {
  private baseurl="http://localhost:8081/api";
  constructor(private http:HttpClient){}

  getReviewByBook(bookId:number){
    //  console.log('Book ID:', bookId);
    return this.http.get<any[]>(`${this.baseurl}/reviews/getByBook/${bookId}`);
  }

  getReviewsByUser(userId:number){
    return this.http.get<any[]>(`{this.baseurl}/reviews/getByUser/${userId}`);
  }

  // addReview(data:any){
  //   return this.http.post(`${this.baseurl}/reviews/add`,data);
  // }
  addReview(userId:number, bookId:number, data:any){
    return this.http.post(`${this.baseurl}/reviews/add/${userId}/${bookId}`,data);
  }

  // updateReview(reviewId:number, data:any){
  //   return this.http.put(`${this.baseurl}/reviews/update/${reviewId}`,data);
  // }

  updateReview(userId:number, bookId:number,data:any){
    return this.http.put(`${this.baseurl}/reviews/update/${userId}/${bookId}`,data);
  }
   
  deleteReview(reviewId:number){
    return this.http.delete(`${this.baseurl}/reviews/delete/${reviewId}`);
  }

}
