import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})

export class Auth {
  private baseUrl="http://localhost:8081/api";

  constructor(private http:HttpClient){}

  register(data:any){
    return this.http.post(`${this.baseUrl}/users/register`,data);
  }
  login(data:any){
    return this.http.post(`${this.baseUrl}/users/login`,data);
  }
  saveUser(user:any){
    localStorage.setItem('user',JSON.stringify(user));
  }
  getUser():any{
    const user=localStorage.getItem('user');
    return user?JSON.parse(user):null;
  }
  getUserId():number{
    // return this.getUser()?.id;
    return this.getUser()?.userId;
  }
  getRole():string{
       return this.getUser()?.role;
  }

  isAdmin():boolean{
    return this.getRole()==='admin';
  }

  isLoggedIn():boolean{
    return !!localStorage.getItem('user');
  }
  logout(){
    localStorage.removeItem('user');
  }
}
