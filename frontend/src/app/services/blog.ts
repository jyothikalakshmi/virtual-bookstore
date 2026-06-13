import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Blog {
  private baseurl="http://localhost:8081/api";
  constructor(private http:HttpClient){}



  getAllBlogs(){
    return this.http.get<any[]>(`${this.baseurl}/blogs/getAll`);
  }
  getBlogById(id:number){
    return this.http.get<any[]>(`${this.baseurl}/blogs/get/${id}`);
  }
  //  createBlog(data: any) {
  //   return this.http.post(`${this.baseurl}/blogs/create`, data);
  // }

  createBlog(userId:number, data:any){
    return this.http.post(`${this.baseurl}/blogs/create/${userId}`,data);
  }

  // updateBlog(id: number, data: any) {
  //   return this.http.put(`${this.baseurl}/blogs/update/${id}`, data);
  // }
  // deleteBlog(id: number) {
  //   return this.http.delete(`${this.baseurl}/blogs/delete/${id}`);
  // }

  updateBlog(blogId:number, userId:number, data:any){
    return this.http.put(`${this.baseurl}/blogs/update/${blogId}/${userId}`,data);
  }

  deleteBlog(blogId:number, userId:number){
    return this.http.delete(`${this.baseurl}/blogs/delete/${blogId}/${userId}`,{responseType:'text'});
  }

}
