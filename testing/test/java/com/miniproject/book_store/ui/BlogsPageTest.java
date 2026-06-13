package com.miniproject.book_store.ui;

import com.miniproject.book_store.base.UiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class BlogsPageTest extends UiBase {

    // Helper — login as user
    private void loginAsUser() throws Exception {
        driver.get(FRONTEND_URL + "/login");
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter email']"))
                .sendKeys("test.gmail.com");
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter password']"))
                .sendKeys("123");
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        System.out.println("✅ Logged in successfully");
    }

    // ─────────────────────────────────────────
    // TEST 0 — Blogs page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Blogs page should load correctly")
    public void testBlogsPageLoads() throws Exception {

        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Check "Blogs" heading is visible
        WebElement heading = driver.findElement(
                By.xpath("//h3[contains(text(),'Blogs')]"));
        Assert.assertTrue(heading.isDisplayed(),
                "Blogs heading should be visible");

        System.out.println("✅ testBlogsPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Blogs are displayed
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Blogs should be displayed on page")
    public void testBlogsAreDisplayed() throws Exception {

        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Check at least one blog card visible
        List<WebElement> blogCards = driver.findElements(
                By.cssSelector(".card"));
        System.out.println("Total blogs displayed: " + blogCards.size());

        Assert.assertTrue(blogCards.size() > 0,
                "At least one blog should be displayed");

        System.out.println("✅ testBlogsAreDisplayed PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Blog title visible
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Blog title should be visible")
    public void testBlogTitleVisible() throws Exception {

        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Check blog title "spring boot book" is visible
        WebElement blogTitle = driver.findElement(
                By.xpath("//h5[contains(text(),'spring boot book')]"));
        Assert.assertTrue(blogTitle.isDisplayed(),
                "Blog title should be visible");

        System.out.println("Blog title: " + blogTitle.getText());
        System.out.println("✅ testBlogTitleVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Write a Blog button visible
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Write a Blog button should be visible after login")
    public void testWriteBlogButtonVisible() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Check "+ Write a Blog" button visible
        WebElement writeBlogBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Write a Blog')]"));
        Assert.assertTrue(writeBlogBtn.isDisplayed(),
                "Write a Blog button should be visible");

        System.out.println("✅ testWriteBlogButtonVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Create a new blog
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Create a new blog successfully")
    public void testCreateBlog() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Click Write a Blog button
        WebElement writeBlogBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Write a Blog')]"));
        writeBlogBtn.click();
        Thread.sleep(1500);

        // Fill title
        WebElement titleInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter blog title']"));
        titleInput.sendKeys("Test Blog Title");

        // Fill content
        WebElement contentInput = driver.findElement(
                By.cssSelector("textarea[placeholder='Write your blog content here...']"));
        contentInput.sendKeys("This is test blog content written by Selenium");

        // Click Submit/Publish button
        WebElement submitBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Post Blog')]"));
        submitBtn.click();
        Thread.sleep(2000);

        // Verify new blog appears on page
        WebElement newBlog = driver.findElement(
                By.xpath("//h5[contains(text(),'Test Blog Title')]"));
        Assert.assertTrue(newBlog.isDisplayed(),
                "New blog should appear on page");

        System.out.println("✅ testCreateBlog PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Blogs page accessible without login
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Blogs page should be accessible without login")
    public void testBlogsPageWithoutLogin() throws Exception {

        // Go directly to blogs without login
        driver.get(FRONTEND_URL + "/blogs");
        Thread.sleep(2000);

        // Should stay on blogs page
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/blogs"),
                "Blogs page should open without login");

        System.out.println("✅ testBlogsPageWithoutLogin PASSED");
    }
}