import { Component } from '@angular/core';

import {Router,RouterLink} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule,RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {

  constructor(public auth:Auth, private router:Router){}
  
  getUsername():string{
    return this.auth.getUser()?.uname || 'User';
  }

  logout(){
    this.auth.logout();
    this.router.navigate(['/login']);
  }

}
