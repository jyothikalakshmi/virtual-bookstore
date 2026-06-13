import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Order {

  private baseurl="http://localhost:8081/api";
  constructor(private http:HttpClient){}

  placeOrder(userId:number){
    return this.http.post(`${this.baseurl}/orders/place/${userId}`,{});
  }
  getOrdersByUser(userId:number){
    return this.http.get<any[]>(`${this.baseurl}/orders/getOrders/${userId}`);
  }
  getOrderDetails(orderId:number){
    return this.http.get<any[]>(`${this.baseurl}/orders/orderDetails/${orderId}`);
  }
}
