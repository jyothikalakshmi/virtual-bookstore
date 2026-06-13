import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Book {
  private baseurl= "http://localhost:8081/api";
  constructor(private http:HttpClient){}

  getAllBooks(){
    return this.http.get<any[]>(`${this.baseurl}/books/allBooks`);
  }

  getBooksById(id:number){
    return this.http.get<any[]>(`${this.baseurl}/books/${id}`);
  }
   getBooksByCategory(category:string){
    return this.http.get<any[]>(`${this.baseurl}/books/category/${category}`);
  }
   getBooksByAuthor(author:string){
    return this.http.get<any[]>(`${this.baseurl}/books/author/${author}`);
  }
   getBooksByTitle(title:string){
    return this.http.get<any[]>(`${this.baseurl}/books/title/${title}`);
  }

  addBook(userId:number, data:any){
    return this.http.post(`${this.baseurl}/books/add/${userId}`,data);
  }
  updateBook(bookId:number, userId:number,data:any){
    return this.http.put(`${this.baseurl}/books/update/${bookId}/by/${userId}`,data);
  }
  deleteBook(bookId:number, userId:number){
    return this.http.delete(`${this.baseurl}/books/delete/${bookId}/by/${userId}`);
  }

}
