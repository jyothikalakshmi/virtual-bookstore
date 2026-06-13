# 🧪 Virtual BookStore — Testing

Comprehensive test suite for the Virtual BookStore application covering API testing and UI testing.

---

## 🛠️ Tech Stack

| Tool | Purpose | Version |
|------|---------|---------|
| TestNG | Test runner and framework | 7.11.0 |
| OkHttp | API testing (HTTP client) | 4.12.0 |
| Selenium | UI / Browser automation | 4.18.1 |
| Maven Surefire | Test execution via Maven | 2.22.2 |
| org.json | JSON parsing in tests | 20240303 |

---

## 📁 Project Structure

```
src/test/java/com/miniproject/book_store/
│
├── base/
│   ├── ApiBase.java          → Shared setup for API tests
│   └── UiBase.java           → Shared setup for UI tests
│
├── api/                      → API Tests (OkHttp + TestNG)
│   ├── UserApiTest.java      → Tests for /api/users
│   ├── BookApiTest.java      → Tests for /api/books
│   ├── CartApiTest.java      → Tests for /api/cart
│   ├── OrderApiTest.java     → Tests for /api/orders
│   ├── BlogApiTest.java      → Tests for /api/blogs
│   └── ReviewApiTest.java    → Tests for /api/reviews
│
└── ui/                       → UI Tests (Selenium + TestNG)
    ├── LoginTest.java        → Tests for login page
    ├── RegisterTest.java     → Tests for register page
    ├── BooksPageTest.java    → Tests for books listing page
    ├── CartPageTest.java     → Tests for cart page
    ├── DashboardTest.java    → Tests for dashboard page
    ├── BookDetailTest.java   → Tests for book detail page
    └── BlogsPageTest.java    → Tests for blogs page
```

---

## 📊 Test Coverage

### API Tests — 43 Tests

| Test Class | Endpoints Tested | Tests |
|------------|-----------------|-------|
| UserApiTest | register, login, getUserById | 7 |
| BookApiTest | getAllBooks, getById, getByAuthor, getByCategory, addBook, deleteBook | 8 |
| CartApiTest | addToCart, getCart, updateQty, removeItem, clearCart | 7 |
| OrderApiTest | placeOrder, getOrders, getOrderDetails | 7 |
| BlogApiTest | createBlog, updateBlog, deleteBlog, getBlogById, getAllBlogs | 7 |
| ReviewApiTest | addReview, updateReview, deleteReview, getByBook, getByUser | 7 |

### UI Tests — 36 Tests

| Test Class | Page Tested | Tests |
|------------|------------|-------|
| LoginTest | /login | 4 |
| RegisterTest | /register | 3 |
| BooksPageTest | /books | 6 |
| CartPageTest | /cart | 4 |
| DashboardTest | /dashboard | 7 |
| BookDetailTest | /books/:id | 6 |
| BlogsPageTest | /blogs | 6 |

---

## ⚙️ Prerequisites

Before running tests make sure:

```
✅ MySQL is running
✅ Spring Boot backend running on port 8081
✅ Angular frontend running on port 4200 (for UI tests)
✅ Microsoft Edge browser installed (for Selenium tests)
```

---

## 🚀 Running Tests

### Run All Tests (API + UI)

```bash
cd book_store
.\mvnw test
```

### Run Only API Tests

Comment out UI Tests section in `testng.xml` and run:

```bash
.\mvnw test
```

### Run a Single Test Class from IntelliJ

Right-click on any test file → **Run**

---

## 📋 testng.xml

```xml
<suite name="BookStore Test Suite" verbose="2">

    <test name="API Tests">
        <classes>
            <class name="com.miniproject.book_store.api.UserApiTest"/>
            <class name="com.miniproject.book_store.api.BookApiTest"/>
            <class name="com.miniproject.book_store.api.CartApiTest"/>
            <class name="com.miniproject.book_store.api.OrderApiTest"/>
            <class name="com.miniproject.book_store.api.BlogApiTest"/>
            <class name="com.miniproject.book_store.api.ReviewApiTest"/>
        </classes>
    </test>

    <test name="UI Tests">
        <classes>
            <class name="com.miniproject.book_store.ui.LoginTest"/>
            <class name="com.miniproject.book_store.ui.RegisterTest"/>
            <class name="com.miniproject.book_store.ui.BooksPageTest"/>
            <class name="com.miniproject.book_store.ui.CartPageTest"/>
            <class name="com.miniproject.book_store.ui.DashboardTest"/>
            <class name="com.miniproject.book_store.ui.BookDetailTest"/>
            <class name="com.miniproject.book_store.ui.BlogsPageTest"/>
        </classes>
    </test>

</suite>
```

---

## 📄 Test Reports

After running `.\mvnw test`, reports are generated at:

```
target/surefire-reports/
├── BookStore Test Suite/
│   ├── API Tests.html        ← API test results
│   ├── UI Tests.html         ← UI test results
│   └── testng-failed.xml     ← Failed tests only
├── index.html                ← Full report (open in browser)
└── emailable-report.html     ← Simple summary report
```

Open `index.html` in any browser to view the full test report.

---

## 🧠 Testing Approach

### API Testing — Integration Testing
```
Test code (OkHttp)
      ↓
HTTP call to real server (port 8081)
      ↓
Spring Boot processes request
      ↓
Service → Repository → MySQL DB
      ↓
Response validated by TestNG assertions
```

### UI Testing — End to End Testing
```
Test code (Selenium)
      ↓
Opens real Edge browser
      ↓
Navigates to localhost:4200
      ↓
Clicks buttons, fills forms
      ↓
Verifies page behavior
      ↓
Browser closes after each test
```

---

## 🐛 Known Bugs Found During Testing

| Bug | Location | Description |
|-----|----------|-------------|
| 1 | OrderController | Invalid userId returns 500 instead of 404 |
| 2 | OrderController | Invalid orderId returns 500 instead of 400 |
| 3 | BlogController | Invalid blogId returns 500 instead of 404 |
| 4 | login.ts | Wrong password → 401 received but error not shown on UI |
| 5 | register.ts | Empty fields → no validation, navigates away |
| 6 | carts.ts | Remove item → cart not refreshing after removal |

---

## 📌 Notes

- API tests use **unique emails** with timestamps to avoid duplicate data issues
- UI tests use **Microsoft Edge** browser via Selenium Manager (no manual driver download needed)
- Each UI test independently logs in and sets up its own state
- Tests run in priority order within each class (priority = 0, 1, 2...)
