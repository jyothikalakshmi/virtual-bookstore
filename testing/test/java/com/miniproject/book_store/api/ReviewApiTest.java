package com.miniproject.book_store.api;

import com.miniproject.book_store.base.ApiBase;
import okhttp3.*;
        import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReviewApiTest extends ApiBase {

    private static final int USER_ID = 1;
    private static final int BOOK_ID = 1;
    private static final int VALID_REVIEW_ID = 3;
    private static int createdReviewId = -1;

    // ─────────────────────────────────────────
    // TEST 0 — Get reviews by book
    // GET /api/reviews/getByBook/{bid}
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Get reviews by book ID")
    public void testGetReviewsByBook() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/getByBook/" + BOOK_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get reviews by book should return 200");

        System.out.println("✅ testGetReviewsByBook PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Get reviews by user
    // GET /api/reviews/getByUser/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Get reviews by user ID")
    public void testGetReviewsByUser() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/getByUser/" + USER_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get reviews by user should return 200");

        System.out.println("✅ testGetReviewsByUser PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Add review for ordered book
    // POST /api/reviews/add/{uid}/{bid}
    // user 1 ordered book 1 so this should work
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Add review for ordered book")
    public void testAddReview() throws Exception {

        String body = """
                {
                    "rating": 5,
                    "review_text": "Test review comment"
                }
                """;

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/add/" + USER_ID + "/" + BOOK_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Add review should return 200");
        Assert.assertTrue(responseBody.contains("Test review comment"),
                "Response should contain review text");

        // Capture review ID for update and delete tests
        createdReviewId = new JSONObject(responseBody).getInt("review_id");
        System.out.println("Created Review ID: " + createdReviewId);

        System.out.println("✅ testAddReview PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Add review for book not ordered
    // POST /api/reviews/add/{uid}/{bid}
    // user 1 never ordered book 19 → should fail
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Add review for non ordered book should fail")
    public void testAddReviewNotOrdered() throws Exception {

        String body = """
                {
                    "rating": 3,
                    "review_text": "Should not be allowed"
                }
                """;

        // book 19 — user 1 never ordered this
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/add/" + USER_ID + "/19")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 403,
                "Review for non ordered book should return 403");

        System.out.println("✅ testAddReviewNotOrdered PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Update review
    // PUT /api/reviews/update/{uid}/{rid}
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Update review successfully")
    public void testUpdateReview() throws Exception {

        Assert.assertNotEquals(createdReviewId, -1,
                "createdReviewId must be set from testAddReview");

        String body = """
                {
                    "rating": 4,
                    "review_text": "Updated review comment"
                }
                """;

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/update/" + USER_ID + "/" + createdReviewId)
                .addHeader("Content-Type", "application/json")
                .put(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Update review should return 200");
        Assert.assertTrue(responseBody.contains("Updated review comment"),
                "Response should contain review text");

        System.out.println("✅ testUpdateReview PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Update review by wrong user
    // PUT /api/reviews/update/{uid}/{rid}
    // user 2 trying to update user 1's review
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Update review by wrong user should return 403")
    public void testUpdateReviewWrongUser() throws Exception {

        Assert.assertNotEquals(createdReviewId, -1,
                "createdReviewId must be set from testAddReview");

        String body = """
                {
                    "rating": 1,
                    "review_text": "Should not be allowed"
                }
                """;

        // User 2 trying to update review created by user 1
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/update/2/" + createdReviewId)
                .addHeader("Content-Type", "application/json")
                .put(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 403,
                "Wrong user updating review should return 403");

        System.out.println("✅ testUpdateReviewWrongUser PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Delete review
    // DELETE /api/reviews/delete/{rid}
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Delete review successfully")
    public void testDeleteReview() throws Exception {

        Assert.assertNotEquals(createdReviewId, -1,
                "createdReviewId must be set from testAddReview");

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/reviews/delete/" + createdReviewId)
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 200,
                "Delete review should return 200");

        System.out.println("✅ testDeleteReview PASSED");
    }
}