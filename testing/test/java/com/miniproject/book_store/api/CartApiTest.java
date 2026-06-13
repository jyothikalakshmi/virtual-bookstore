package com.miniproject.book_store.api;

import com.miniproject.book_store.base.ApiBase;
import okhttp3.*;
        import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartApiTest extends ApiBase {

    private static final int USER_ID = 1;
    private static final int BOOK_ID = 1;
    private static int cartItemId = -1;

    // ─────────────────────────────────────────
    // TEST 0 — Add book to cart
    // POST /api/cart/add/{uid}/{bid}
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Add book to cart")
    public void testAddToCart() throws Exception {

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

        // Capture cartItemId for use in later tests
        cartItemId = new JSONObject(responseBody).getInt("cart_item_id");
        System.out.println("Cart Item ID captured: " + cartItemId);

        System.out.println("✅ testAddToCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Get cart of user
    // GET /api/cart/getCart/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Get cart of user")
    public void testGetCart() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/getCart/" + USER_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get cart should return 200");
        Assert.assertTrue(responseBody.contains("cart_item_id"),
                "Response should contain cart items");

        System.out.println("✅ testGetCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Update quantity of cart item
    // PUT /api/cart/update/{itemId}/{qty}
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Update cart item quantity")
    public void testUpdateQuantity() throws Exception {

        Assert.assertNotEquals(cartItemId, -1,
                "cartItemId must be set from testAddToCart");

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/update/" + cartItemId + "/3")
                .addHeader("Content-Type", "application/json")
                .put(RequestBody.create("", JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Update quantity should return 200");
        Assert.assertTrue(responseBody.contains("3"),
                "Response should reflect updated quantity");

        System.out.println("✅ testUpdateQuantity PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Remove item from cart
    // DELETE /api/cart/remove/{itemId}
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Remove item from cart")
    public void testRemoveFromCart() throws Exception {

        Assert.assertNotEquals(cartItemId, -1,
                "cartItemId must be set from testAddToCart");

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/remove/" + cartItemId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Remove from cart should return 200");
        Assert.assertTrue(responseBody.contains("Item removed"),
                "Response should confirm item removed");

        System.out.println("✅ testRemoveFromCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Add item again to test clear cart
    // POST /api/cart/add/{uid}/{bid}
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Add item again before clearing cart")
    public void testAddToCartAgain() throws Exception {

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
                "Add to cart again should return 200");

        System.out.println("✅ testAddToCartAgain PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Clear entire cart
    // DELETE /api/cart/clear/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Clear entire cart of user")
    public void testClearCart() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/clear/" + USER_ID)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Clear cart should return 200");
        Assert.assertTrue(responseBody.contains("Cart cleared"),
                "Response should confirm cart cleared");

        System.out.println("✅ testClearCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Add to cart with invalid user
    // POST /api/cart/add/99999/1
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Add to cart with invalid user should return 404")
    public void testAddToCartInvalidUser() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/cart/add/99999/" + BOOK_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create("", JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 404,
                "Invalid user should return 404");

        System.out.println("✅ testAddToCartInvalidUser PASSED");
    }
}