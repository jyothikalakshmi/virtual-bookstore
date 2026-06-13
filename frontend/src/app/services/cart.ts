import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Cart {

  private baseurl="http://localhost:8081/api";
  constructor(private http:HttpClient){}

  getCartByUser(userId:number){
    return this.http.get<any>(`${this.baseurl}/cart/getCart/${userId}`);
  }

  // addToCart(data:any){
  //   return this.http.post(`${this.baseurl}/cart/add`,data);
  // }

  addToCart(userId:number, bookId:number){
    return this.http.post(`${this.baseurl}/cart/add/${userId}/${bookId}`,{});
  }

 
  // updateQuantity(cartItemId:number, quantity:number){
  //   return this.http.put(`${this.baseurl}/cart/update/${cartItemId}`,{quantity});
  // }

  updateQuantity(itemId:number,qty:number){
    return this.http.put(`${this.baseurl}/cart/update/${itemId}/${qty}`,{});
  }

   removeFromCart(cartItemId:number){
    return this.http.delete(`${this.baseurl}/cart/remove/${cartItemId}`);
  }


  clearCart(userId:number){
    return this.http.delete(`${this.baseurl}/cart/clear/${userId}`);
  }

}
