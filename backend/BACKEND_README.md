# 📦 Virtual BookStore — Backend

Spring Boot REST API backend for the Virtual BookStore application.

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Data JPA
- Hibernate 7
- MySQL 8.0
- Lombok
- Maven

---

## 📁 Project Structure

```
src/main/java/com/miniproject/book_store/
│
├── BookStoreApplication.java       → Main entry point
│
├── config/
│   └── CorsConfig.java             → CORS configuration
│
├── Controller/
│   ├── UserController.java         → /api/users
│   ├── BookController.java         → /api/books
│   ├── CartController.java         → /api/cart
│   ├── OrderController.java        → /api/orders
│   ├── BlogController.java         → /api/blogs
│   └── ReviewController.java       → /api/reviews
│
├── Service/
│   ├── UserService.java
│   ├── BookService.java
│   ├── CartService.java
│   ├── OrderService.java
│   ├── BlogService.java
│   └── ReviewService.java
│
├── Repository/
│   ├── UserRepository.java
│   ├── BookRepository.java
│   ├── CartRepository.java
│   ├── CartItemsRepository.java
│   ├── OrderRepository.java
│   ├── OrderItemsRepository.java
│   ├── BlogsRepository.java
│   └── ReviewsRepository.java
│
└── Entity/
    ├── User.java
    ├── BookEntity.java
    ├── Cart.java
    ├── CartItems.java
    ├── Orders.java
    ├── OrderItems.java
    ├── Blogs.java
    └── Reviews.java
```

---

## ⚙️ Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🚀 Running the Application

### Prerequisites
- Java 21
- MySQL 8.0 running
- Database `book_store` created

### Steps

```bash
# Clone and navigate
cd book_store

# Run
./mvnw spring-boot:run
```

Application starts at: `http://localhost:8081`

---

## 📡 API Endpoints

### 👤 User APIs — `/api/users`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users/register` | Register new user |
| POST | `/api/users/login` | Login user |
| GET | `/api/users/{id}` | Get user by ID |

### 📖 Book APIs — `/api/books`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/books/allBooks` | Get all books |
| GET | `/api/books/{bookId}` | Get book by ID |
| GET | `/api/books/author/{author}` | Get books by author |
| GET | `/api/books/category/{category}` | Get books by category |
| GET | `/api/books/title/{bname}` | Get books by title |
| POST | `/api/books/add/{userId}` | Add book (Admin only) |
| POST | `/api/books/update/{bookId}/by/{userId}` | Update book (Admin only) |
| DELETE | `/api/books/delete/{bookId}/by/{userId}` | Delete book (Admin only) |

### 🛒 Cart APIs — `/api/cart`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/cart/add/{uid}/{bid}` | Add book to cart |
| GET | `/api/cart/getCart/{uid}` | Get cart of user |
| PUT | `/api/cart/update/{itemId}/{qty}` | Update item quantity |
| DELETE | `/api/cart/remove/{itemId}` | Remove item from cart |
| DELETE | `/api/cart/clear/{uid}` | Clear entire cart |

### 📦 Order APIs — `/api/orders`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders/place/{uid}` | Place order from cart |
| GET | `/api/orders/getOrders/{uid}` | Get orders of user |
| GET | `/api/orders/orderDetails/{oid}` | Get order details |

### ✍️ Blog APIs — `/api/blogs`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/blogs/create/{uid}` | Create blog |
| PUT | `/api/blogs/update/{bid}/{uid}` | Update blog |
| DELETE | `/api/blogs/delete/{bid}/{uid}` | Delete blog |
| GET | `/api/blogs/get/{bid}` | Get blog by ID |
| GET | `/api/blogs/getAll` | Get all blogs |

### ⭐ Review APIs — `/api/reviews`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/reviews/add/{uid}/{bid}` | Add review |
| PUT | `/api/reviews/update/{uid}/{rid}` | Update review |
| DELETE | `/api/reviews/delete/{rid}` | Delete review |
| GET | `/api/reviews/getByBook/{bid}` | Get reviews by book |
| GET | `/api/reviews/getByUser/{uid}` | Get reviews by user |

---

## 🗃️ Database Schema

```
users         → userId, uname, phno, email, password, role
books         → book_id, bname, author, price, description, category, stock
cart          → cart_id, user_id
cart_items    → cart_item_id, cart_id, book_id, quantity
orders        → order_id, user_id, order_date, totalPrice
order_items   → order_item_id, order_id, book_id, quantity, price
blogs         → blog_id, user_id, title, content
reviews       → review_id, user_id, book_id, rating, review_text
```

---

## 🔒 Role Based Access

```
USER  → can browse, cart, order, review, blog
ADMIN → all USER permissions + add/edit/delete books
```
