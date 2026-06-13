package com.miniproject.book_store.api;

import com.miniproject.book_store.base.ApiBase;
import okhttp3.*;
        import org.testng.Assert;
import org.testng.annotations.Test;

public class BookApiTest extends ApiBase {

    // ─────────────────────────────────────────
    // Shared data across tests
    // ─────────────────────────────────────────
    private static final int ADMIN_ID = 3;
    private static final int VALID_BOOK_ID = 1;
    private static final int INVALID_BOOK_ID = 99999;
    private static final String VALID_AUTHOR = "xyz";
    private static final String VALID_CATEGORY = "web development";
    private static final String UNIQUE_BOOK_NAME =
            "TestBook" + System.currentTimeMillis();

    // Will store the ID of book created in addBook test
    private static int createdBookId = -1;

    // ─────────────────────────────────────────
    // TEST 0 — Get all books
    // GET /api/books/allBooks
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Get all books")
    public void testGetAllBooks() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/allBooks")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get all books should return 200");
        Assert.assertTrue(responseBody.contains("book_id"),
                "Response should contain book_id field");

        System.out.println("✅ testGetAllBooks PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Get book by valid ID
    // GET /api/books/{bookId}
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Get book by valid ID")
    public void testGetBookById() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/" + VALID_BOOK_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get book by ID should return 200");
        Assert.assertTrue(responseBody.contains("book_id"),
                "Response should contain book_id");

        System.out.println("✅ testGetBookById PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Get book by invalid ID
    // GET /api/books/99999
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Get book by invalid ID should return 404")
    public void testGetBookByInvalidId() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/" + INVALID_BOOK_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 404,
                "Invalid book ID should return 404");

        System.out.println("✅ testGetBookByInvalidId PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Get books by author
    // GET /api/books/author/{author}
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Get books by author")
    public void testGetBooksByAuthor() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/author/" + VALID_AUTHOR)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get books by author should return 200");
        Assert.assertTrue(responseBody.contains(VALID_AUTHOR),
                "Response should contain author name");

        System.out.println("✅ testGetBooksByAuthor PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Get books by invalid author
    // GET /api/books/author/unknownauthor
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Get books by invalid author should return 400")
    public void testGetBooksByInvalidAuthor() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/author/unknownauthor")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 400,
                "Invalid author should return 400");

        System.out.println("✅ testGetBooksByInvalidAuthor PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Get books by category
    // GET /api/books/category/{category}
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Get books by category")
    public void testGetBooksByCategory() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/category/" + VALID_CATEGORY)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get books by category should return 200");
        Assert.assertTrue(responseBody.contains(VALID_CATEGORY),
                "Response should contain category name");

        System.out.println("✅ testGetBooksByCategory PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Add book as admin
    // POST /api/books/add/{userId}
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Add a new book as admin")
    public void testAddBook() throws Exception {

        String body = """
                {
                    "bname": "%s",
                    "author": "Test Author",
                    "price": 299.0,
                    "description": "Test Description",
                    "category": "Test Category",
                    "stock": 10
                }
                """.formatted(UNIQUE_BOOK_NAME);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/add/" + ADMIN_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Add book should return 200");
        Assert.assertTrue(responseBody.contains(UNIQUE_BOOK_NAME),
                "Response should contain book name");

        System.out.println("✅ testAddBook PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 7 — Add book as non admin should fail
    // POST /api/books/add/{userId}
    // ─────────────────────────────────────────
    @Test(priority = 7, description = "Add book as non admin should return 403")
    public void testAddBookNonAdmin() throws Exception {

        String body = """
                {
                    "bname": "Some Book",
                    "author": "Some Author",
                    "price": 199.0,
                    "description": "Some Description",
                    "category": "Some Category",
                    "stock": 5
                }
                """;

        // Using user ID 1 which is a regular USER not ADMIN
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/add/1")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 403,
                "Non admin should not be able to add book");

        System.out.println("✅ testAddBookNonAdmin PASSED");
    }
}