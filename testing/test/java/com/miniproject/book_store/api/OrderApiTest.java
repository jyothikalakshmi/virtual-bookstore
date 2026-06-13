package com.miniproject.book_store.api;

import com.miniproject.book_store.base.ApiBase;
import okhttp3.*;
        import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrderApiTest extends ApiBase {

    private static final int USER_ID = 1;
    private static final int BOOK_ID = 1;
    private static int createdOrderId = -1;

    // ─────────────────────────────────────────
    // TEST 0 — Add book to cart first
    // POST /api/cart/add/{uid}/{bid}
    // Cart must have items before placing order
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Add book to cart before placing order")
    public void testAddToCartBeforeOrder() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/add/" + USER_ID + "/" + BOOK_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("", JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Add to cart should return 200");
        Assert.assertTrue(responseBody.contains("cart_item_id"),
                "Response should contain cart_item_id");

        System.out.println("✅ testAddToCartBeforeOrder PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Place order
    // POST /api/orders/place/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Place order successfully")
    public void testPlaceOrder() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/place/" + USER_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("", JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Place order should return 200");
        Assert.assertTrue(responseBody.contains("order_id"),
                "Response should contain order_id");
        Assert.assertTrue(responseBody.contains("totalPrice"),
                "Response should contain totalPrice");

        // Capture order_id for use in later tests
        createdOrderId = new JSONObject(responseBody).getInt("order_id");
        System.out.println("Created Order ID: " + createdOrderId);

        System.out.println("✅ testPlaceOrder PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Place order with empty cart
    // POST /api/orders/place/{uid}
    // Cart is now empty after TEST 1
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Place order with empty cart should fail")
    public void testPlaceOrderEmptyCart() throws Exception {

        // Cart is empty now since TEST 1 already placed the order
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/place/" + USER_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("", JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        // Should fail — cart is empty
        Assert.assertNotEquals(response.code(), 200,
                "Placing order with empty cart should not return 200");

        System.out.println("✅ testPlaceOrderEmptyCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Get orders of user
    // GET /api/orders/getOrders/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Get all orders of user")
    public void testGetOrdersByUser() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/getOrders/" + USER_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get orders should return 200");
        Assert.assertTrue(responseBody.contains("order_id"),
                "Response should contain order_id");

        System.out.println("✅ testGetOrdersByUser PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Get orders of invalid user
    // GET /api/orders/getOrders/99999
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Get orders of invalid user should return 404")
    public void testGetOrdersInvalidUser() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/getOrders/99999")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 404,
                "Invalid user should return 404");

        System.out.println("✅ testGetOrdersInvalidUser PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Get order details
    // GET /api/orders/orderDetails/{oid}
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Get order details by order ID")
    public void testGetOrderDetails() throws Exception {

        Assert.assertNotEquals(createdOrderId, -1,
                "createdOrderId must be set from testPlaceOrder");

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/orderDetails/" + createdOrderId)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get order details should return 200");
        Assert.assertTrue(responseBody.contains("order_id"),
                "Response should contain order details");

        System.out.println("✅ testGetOrderDetails PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Get order details with invalid ID
    // GET /api/orders/orderDetails/99999
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Get order details with invalid ID should return 400")
    public void testGetOrderDetailsInvalidId() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/orders/orderDetails/99999")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 400,
                "Invalid order ID should return 400");

        System.out.println("✅ testGetOrderDetailsInvalidId PASSED");
    }
}