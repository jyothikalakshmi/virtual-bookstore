import { Routes } from '@angular/router';

import {Login} from './pages/login/login';
import {Register} from './pages/register/register';
import {Books} from './pages/books/books';
import {BookDetail} from './pages/book-detail/book-detail';
import {Carts} from './pages/carts/carts';
import {Blogs} from './pages/blogs/blogs';
import { Dashboard } from './pages/dashboard/dashboard';

export const routes: Routes = [
    // {path:'',redirectTo:'login',pathMatch:'full'},
    // {path:'login',component:Login},
    // {path:'register',component:Register},
    // {path:'books',component:Books},
    // {path:'books/:id',component:BookDetail},
    // {path:'cart',component:Carts},
    // {path:'blogs',component:Blogs}


    {path:'',redirectTo:'home',pathMatch:'full'},
    {path:'home',loadComponent:()=>import('./pages/home/home').then(m=>m.Home)},
    {path:'login',loadComponent:()=>import('./pages/login/login').then(m=>m.Login)},
    {path:'register',loadComponent:()=>import('./pages/register/register').then(m=>m.Register)},
    {path:'books',loadComponent:()=>import('./pages/books/books').then(m=>m.Books)},
    {path:'books/:id',loadComponent:()=>import('./pages/book-detail/book-detail').then(m=>m.BookDetail)},
    {path:'cart',loadComponent:()=>import('./pages/carts/carts').then(m=>m.Carts)},
    {path:'blogs',loadComponent:()=>import('./pages/blogs/blogs').then(m=>m.Blogs)},
    {path:'dashboard',loadComponent:()=>import('./pages/dashboard/dashboard').then(m=>m.Dashboard)}


];
