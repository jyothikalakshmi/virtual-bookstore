package com.miniproject.book_store.api;

import com.miniproject.book_store.base.ApiBase;
import okhttp3.*;
        import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BlogApiTest extends ApiBase {

    private static final int USER_ID = 1;
    private static final int VALID_BLOG_ID = 1;
    private static int createdBlogId = -1;
    private static final String UNIQUE_TITLE =
            "Test Blog " + System.currentTimeMillis();

    // ─────────────────────────────────────────
    // TEST 0 — Get all blogs
    // GET /api/blogs/getAll
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Get all blogs")
    public void testGetAllBlogs() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/getAll")
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get all blogs should return 200");
        Assert.assertTrue(responseBody.contains("blog_id"),
                "Response should contain blog_id");

        System.out.println("✅ testGetAllBlogs PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Get blog by valid ID
    // GET /api/blogs/get/{bid}
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Get blog by valid ID")
    public void testGetBlogById() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/get/" + VALID_BLOG_ID)
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Get blog by ID should return 200");
        Assert.assertTrue(responseBody.contains("blog_id"),
                "Response should contain blog_id");
        Assert.assertTrue(responseBody.contains("spring boot book"),
                "Response should contain blog title");

        System.out.println("✅ testGetBlogById PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Get blog by invalid ID
    // GET /api/blogs/get/99999
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Get blog by invalid ID should return 404")
    public void testGetBlogByInvalidId() throws Exception {

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/get/99999")
                .get()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 404,
                "Invalid blog ID should return 404");

        System.out.println("✅ testGetBlogByInvalidId PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Create blog
    // POST /api/blogs/create/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Create a new blog successfully")
    public void testCreateBlog() throws Exception {

        String body = """
                {
                    "title": "%s",
                    "content": "This is test blog content"
                }
                """.formatted(UNIQUE_TITLE);

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/create/" + USER_ID)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Create blog should return 200");
        Assert.assertTrue(responseBody.contains(UNIQUE_TITLE),
                "Response should contain blog title");

        // Capture blog ID for update and delete tests
        createdBlogId = new JSONObject(responseBody).getInt("blog_id");
        System.out.println("Created Blog ID: " + createdBlogId);

        System.out.println("✅ testCreateBlog PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Update blog
    // PUT /api/blogs/update/{bid}/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Update blog successfully")
    public void testUpdateBlog() throws Exception {

        Assert.assertNotEquals(createdBlogId, -1,
                "createdBlogId must be set from testCreateBlog");

        String body = """
                {
                    "title": "Updated Title",
                    "content": "Updated blog content"
                }
                """;

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/update/" + createdBlogId + "/" + USER_ID)
                .addHeader("Content-Type", "application/json")
                .put(RequestBody.create(body, JSON))
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Update blog should return 200");
        Assert.assertTrue(responseBody.contains("Updated Title"),
                "Response should contain updated title");

        System.out.println("✅ testUpdateBlog PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Delete blog
    // DELETE /api/blogs/delete/{bid}/{uid}
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Delete blog successfully")
    public void testDeleteBlog() throws Exception {

        Assert.assertNotEquals(createdBlogId, -1,
                "createdBlogId must be set from testCreateBlog");

        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/delete/" + createdBlogId + "/" + USER_ID)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        System.out.println("Response Code : " + response.code());
        System.out.println("Response Body : " + responseBody);

        Assert.assertEquals(response.code(), 200,
                "Delete blog should return 200");
        Assert.assertTrue(responseBody.contains("Blog deleted successfully"),
                "Response should confirm deletion");

        System.out.println("✅ testDeleteBlog PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Delete blog with wrong user
    // DELETE /api/blogs/delete/{bid}/{uid}
    // user 2 trying to delete user 1's blog
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Delete blog by wrong user should return 403")
    public void testDeleteBlogWrongUser() throws Exception {

        // User 2 trying to delete blog 1 which belongs to user 1
        Request request = new Request.Builder()
                .url(BASE_URL + "/api/blogs/delete/" + VALID_BLOG_ID + "/2")
                .delete()
                .build();

        Response response = client.newCall(request).execute();

        System.out.println("Response Code : " + response.code());

        Assert.assertEquals(response.code(), 403,
                "Wrong user deleting blog should return 403");

        System.out.println("✅ testDeleteBlogWrongUser PASSED");
    }
}