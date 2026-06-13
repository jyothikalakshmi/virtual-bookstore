import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';
import {Router,RouterLink} from '@angular/router';
import { Auth } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  credentials={email:'',password:''};
  error='';
  
  constructor(private auth:Auth,private router:Router){}

  login(){
    this.auth.login(this.credentials).subscribe({
      next:(user:any)=>{
        this.auth.saveUser(user);
        this.router.navigate(['/dashboard']);
      },
      error:()=>{
        this.error='Invalid email or password';
      }
    });
  }

}
