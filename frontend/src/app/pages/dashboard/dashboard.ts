import { Component,OnInit } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Navbar } from '../../components/navbar/navbar';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink,CommonModule,Navbar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  
  username='';
  isAdmin=false;

  constructor(private auth:Auth,private router:Router){}

  ngOnInit(){
    this.username=this.auth.getUser()?.uname||'Reader';
    this.isAdmin=this.auth.isAdmin();
  }

  goToBooks(){
    this.router.navigate(['/books']);
  }
  goToBlogs(){
    this.router.navigate(['/blogs']);
  }
  goToCart(){
    this.router.navigate(['/cart']);
  }

}
