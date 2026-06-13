# 📚 Virtual BookStore

A full-stack virtual bookstore web application where users can browse books, manage their cart, place orders, write reviews, and share blogs. Admins can manage the book inventory.

---

## 🛠️ Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| Backend | Java, Spring Boot, Spring Data JPA | 21, 4.0.5 |
| Frontend | Angular, TypeScript, Bootstrap | 21 |
| Database | MySQL | 8.0 |
| API Testing | TestNG, OkHttp | 7.11, 4.12 |
| UI Testing | Selenium WebDriver, TestNG | 4.18, 7.11 |
| Build Tool | Maven | - |

---

## ✨ Features

- 👤 User registration and login
- 📖 Browse, search, filter and sort books
- 🛒 Add to cart, update quantity, remove items
- 📦 Place orders from cart
- ⭐ Write reviews (only for purchased books)
- ✍️ Community blogs — create, edit, delete
- 🛡️ Admin panel — add, edit, delete books
- 🔒 Role based access — USER and ADMIN

---

## 🚀 Getting Started

### Prerequisites

- Java 21
- Node.js 18+
- MySQL 8.0
- Maven

### Step 1 — Setup Database

```sql
CREATE DATABASE book_store;
```

### Step 2 — Configure Backend

Edit `book_store/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/book_store
spring.datasource.username=root
spring.datasource.password=root
server.port=8081
```

### Step 3 — Start Backend

```bash
cd book_store
./mvnw spring-boot:run
```

Runs at → `http://localhost:8081`

### Step 4 — Start Frontend

```bash
cd bookstore-frontend
npm install
ng serve
```

Runs at → `http://localhost:4200`

---

## 🧪 Running Tests

Ensure both backend (port 8081) and frontend (port 4200) are running, then:

```bash
cd bookstore-tests
.\mvnw test
```

Test report generated at:
```
bookstore-tests/target/surefire-reports/index.html
```

---

## 👥 User Roles

| Role | Permissions |
|------|-------------|
| USER | Browse books, cart, orders, reviews, blogs |
| ADMIN | All USER permissions + add / edit / delete books |

---

