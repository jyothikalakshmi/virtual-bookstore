package com.miniproject.book_store.api;
import com.miniproject.book_store.base.ApiBase;
import org.testng.annotations.Test;

//import io.restassured.http.ContentType;
//import io.restassured.specification.ProxySpecification;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.anyOf;

import okhttp3.*;
import org.testng.Assert;

public class UserApiTest extends ApiBase {

//    @Test(priority = 0, description = "Check if Spring Boot server is reachable")
//    public void testServerIsRunning() {
//        given()
//                .proxy((String) null)
//                .baseUri("http://localhost:8081")
//                .when()
//                .get("/api/books/allBooks")
//                .then()
//                .statusCode(anyOf(equalTo(200), equalTo(404)));
//
//        System.out.println("✅ Server is reachable on port 8081");
//    }
//
//    @Test(priority = 1, description = "Register a new user successfully")
//    public void testRegisterSuccess() {
//        String requestBody = """
//                {
//                    "uname": "Test User",
//                    "email": "testuser@gmail.com",
//                    "password": "test123",
//                    "phno": "9876543210",
//                    "role": "USER"
//                }
//                """;
//        given()
//                .baseUri("http://localhost:8081")
//                .contentType(ContentType.JSON)
//                .body(requestBody)
//                .when()
//                .post("/api/users/register")
//                .then()
//                .statusCode(200)
//                .body("uname", equalTo("Test User"))
//                .body("email", equalTo("testuser@gmail.com"))
//                .body("role", equalTo("USER"))
//                .body("userId", notNullValue());
//
//        System.out.println("✅ testRegisterSuccess PASSED");
//    }

    private static final String TEST_EMAIL =
            "testuser" + System.currentTimeMillis() + "@gmail.com";
    private static final String TEST_PASSWORD = "test123";

    @Test(priority = 0, description = "Check if Spring Boot server is reachable")
    public void testServerIsRunning() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/books/allBooks")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        int code = response.code();

        System.out.println("Server response code: " + code);

        // 200 = books exist in DB
        // 404 = no books yet but server is running
        // Both mean server is UP and reachable
        Assert.assertTrue(
                code == 200 || code == 404,
                "Server should be reachable on port 8081"
        );

        System.out.println("✅ testServerIsRunning PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Register new user
    // POST http://localhost:8081/api/users/register
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Register a new user successfully")
    public void testRegisterSuccess() throws Exception {

//        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";


        String body = """
                {
                    "uname": "Test User",
                   "email":"%s",
                    "password": "%s",
                    "phno": "9876543210",
                    "role": "USER"
                }
                """.formatted(TEST_EMAIL,TEST_PASSWORD);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/register")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Register should return 200");
        Assert.assertTrue(responseBody.contains("Test User"),
                "Response should contain uname");
        Assert.assertTrue(responseBody.contains(TEST_EMAIL),
                "Response should contain email");

        System.out.println("✅ testRegisterSuccess PASSED");
    }

    @Test(priority = 2, description = "Login with correct credentials")
    public void testLoginSuccess() throws Exception {

        String body = """
                {
                    "email": "%s",
                    "password": "%s"
                }
                """.formatted(TEST_EMAIL, TEST_PASSWORD);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/login")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Login should return 200");
        Assert.assertTrue(responseBody.contains(TEST_EMAIL),
                "Response should contain email");

        System.out.println("✅ testLoginSuccess PASSED");
    }

    @Test(priority = 3, description = "Login with wrong password should return 401")
    public void testLoginWrongPassword() throws Exception {

        String body = """
                {
                    "email": "%s",
                    "password": "wrongpassword"
                }
                """.formatted(TEST_EMAIL);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/login")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code: " + response.code());

        Assert.assertEquals(response.code(), 401,
                "Wrong password should return 401");

        System.out.println("✅ testLoginWrongPassword PASSED");
    }

    @Test(priority = 4, description = "Login with non existent email should return 401")
    public void testLoginNonExistentEmail() throws Exception {

        String body = """
                {
                    "email": "nobody@gmail.com",
                    "password": "anypassword"
                }
                """;

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/login")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code: " + response.code());

        Assert.assertEquals(response.code(), 401,
                "Non existent email should return 401");

        System.out.println("✅ testLoginNonExistentEmail PASSED");
    }

    @Test(priority = 5, description = "Get user by valid ID")
    public void testGetUserById() throws Exception {

        // Using ID 1 — assuming at least one user exists in DB
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/1")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get user by ID should return 200");
        Assert.assertTrue(responseBody.contains("userId"),
                "Response should contain userId field");

        System.out.println("✅ testGetUserById PASSED");
    }

    @Test(priority = 6, description = "Get user with invalid ID should return 404")
    public void testGetUserInvalidId() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/users/99999")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code: " + response.code());

        Assert.assertEquals(response.code(), 404,
                "Invalid user ID should return 404");

        System.out.println("✅ testGetUserInvalidId PASSED");
    }

}
