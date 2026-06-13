import { Component } from '@angular/core';

import { FormsModule } from '@angular/forms';
import {Router,RouterLink} from '@angular/router';
import { Auth } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [FormsModule,CommonModule,RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  user={uname:'',email:'',password:'',phno:'', role:'USER'};
  error='';
  success='';

  constructor(private auth:Auth,private router:Router){}

  register(){
    this.auth.register(this.user).subscribe({
      next:()=>{
        this.success="Registered successfully ! please login";
        setTimeout(()=>this.router.navigate(['/login']),1500);
      },
      error:()=>{
        this.error="Registration failed. Try again";
      }
    });
  }
}
