# 🌐 Virtual BookStore — Frontend

Angular frontend for the Virtual BookStore application.

---

## 🛠️ Tech Stack

- Angular 21
- TypeScript
- Bootstrap 5
- RxJS
- Angular Router

---

## 📁 Project Structure

```
src/app/
│
├── pages/
│   ├── home/               → Landing page
│   ├── login/              → Login form
│   ├── register/           → Register form
│   ├── dashboard/          → User dashboard
│   ├── books/              → Books listing page
│   ├── book-detail/        → Book detail + reviews
│   ├── carts/              → Cart management
│   └── blogs/              → Blogs listing + create
│
├── services/
│   ├── auth.ts             → Login / Register API calls
│   ├── book.ts             → Book API calls
│   ├── cart.ts             → Cart API calls
│   ├── order.ts            → Order API calls
│   ├── blog.ts             → Blog API calls
│   └── review.ts           → Review API calls
│
├── components/
│   └── navbar/             → Navigation bar
│
├── app.routes.ts           → Route definitions
└── app.config.ts           → App configuration
```

---

## 🚀 Running the Application

### Prerequisites
- Node.js 18+
- Angular CLI

### Steps

```bash
# Navigate to frontend folder
cd bookstore-frontend

# Install dependencies
npm install

# Start development server
ng serve
```

Application runs at: `http://localhost:4200`

---

## 🗺️ Routes

| Route | Component | Description |
|-------|-----------|-------------|
| `/home` | Home | Landing page |
| `/login` | Login | User login |
| `/register` | Register | User registration |
| `/dashboard` | Dashboard | User home after login |
| `/books` | Books | Browse all books |
| `/books/:id` | BookDetail | Single book detail + reviews |
| `/cart` | Carts | Cart management |
| `/blogs` | Blogs | Community blogs |

---

## ✨ Pages Overview

### 🏠 Home Page
- Landing page for new visitors
- Links to Login and Register

### 🔐 Login Page
- Email and password login
- Navigates to dashboard on success

### 📝 Register Page
- Full name, email, password, phone number
- Navigates to login on success

### 📊 Dashboard
- Welcome message with username
- Quick links to Books, Cart, Blogs
- Admin badge shown for admin users

### 📚 Books Page
- Displays all books in grid layout
- Search by title or author
- Filter by category
- Sort by title or price
- Admin can add, edit, delete books

### 📖 Book Detail Page
- Book name, author, price, stock, description
- Add to Cart button
- Customer Reviews section
- Write a Review form (only for users who ordered the book)

### 🛒 Cart Page
- Lists all cart items
- Increase/decrease quantity
- Remove individual items
- Total price display
- Place Order button
- Order success message after placing order

### ✍️ Blogs Page
- Lists all community blogs
- Write a Blog button for logged in users
- Edit and delete own blogs

---

## 🔗 Backend Connection

The frontend connects to the backend at:
```
http://localhost:8081
```

Make sure the Spring Boot backend is running before starting the frontend.

---

## 👥 Role Based UI

```
USER  → sees Browse Books, Cart, Blogs
ADMIN → additionally sees Add Book, Edit Book, Delete Book buttons
        Admin Access badge shown on dashboard
```
