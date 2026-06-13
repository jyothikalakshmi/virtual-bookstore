import { ChangeDetectorRef, Component,OnInit } from '@angular/core';
import { Order } from '../../services/order';
import { Auth } from '../../services/auth';
import { Cart } from '../../services/cart';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../../components/navbar/navbar';
import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-cart',
  imports: [CommonModule,RouterLink,FormsModule,Navbar],
  templateUrl: './carts.html',
  styleUrl: './carts.css',
})
export class Carts {

  // cart:any=null;
  cartItems:any[]=[];
  userId:number=0;
  totalPrice:number=0;
  orderSuccess=false;
  errorMsg='';
  loading=true;

  constructor( private cartservice:Cart, private orderservice:Order,private auth:Auth, private cdr:ChangeDetectorRef){}
  
  ngOnInit(){
    this.userId=this.auth.getUserId();
    this.loadCart();
  }

   loadCart() {
    this.loading=true;
    this.cartservice.getCartByUser(this.userId).subscribe({
      next: (data) => {
        // this.cart = data;
        // this.cartItems = data.cartItems || data || [];
        this.cartItems=Array.isArray(data)?data:[data];
        this.calculateTotal();
        this.loading=false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.cartItems = [];
        this.loading=false;
        this.cdr.detectChanges();
      }
    });
  }
  //  calculateTotal() {
  //   this.totalPrice = this.cartItems.reduce((sum, item) => {
  //     return sum + (item.price * item.quantity);
  //   }, 0);
  // }

  calculateTotal(){
    this.totalPrice=this.cartItems.reduce((sum:number, item:any)=>{
      // const price=item.price || item.book?.price ||0;
      const price=item.book_id?.price||0;
      return sum+ (price*item.quantity);
    },0);
  }
 
  updateQuantity(cartItemId: number, quantity: number) {
    if (quantity < 1) return;
    this.cartservice.updateQuantity(cartItemId, quantity).subscribe({
      next: () => this.loadCart(),
      error: () => this.errorMsg = 'Failed to update quantity.'
    });
  }

   removeItem(cartItemId: number) {
    this.cartservice.removeFromCart(cartItemId).subscribe({
      next: () => this.loadCart(),
      error: () => this.errorMsg = 'Failed to remove item.'
    });
  }

   placeOrder() {
    this.errorMsg='';
    this.orderservice.placeOrder(this.userId).subscribe({
      next: () => {
        this.orderSuccess = true;
        this.cartItems = [];
        this.totalPrice = 0;
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMsg = 'Failed to place order. Please Try again.';
        this.cdr.detectChanges();
      }
    });
  }
}
