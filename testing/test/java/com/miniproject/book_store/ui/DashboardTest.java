package com.miniproject.book_store.ui;

import com.miniproject.book_store.base.UiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class DashboardTest extends UiBase {

    // Helper method — login
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

    // Helper method — login as admin
    private void loginAsAdmin() throws Exception {
        driver.get(FRONTEND_URL + "/login");
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter email']"))
                .sendKeys("jyo@bookstore.com");
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter password']"))
                .sendKeys("Jyo2004");
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        System.out.println("✅ Logged in as admin");
    }

    // ─────────────────────────────────────────
    // TEST 0 — Dashboard loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Dashboard page should load correctly")
    public void testDashboardLoads() throws Exception {

        loginAsUser();

        // Check welcome message is visible
        WebElement welcome = driver.findElement(
                By.xpath("//h1[contains(text(),'Welcome back')]"));
        Assert.assertTrue(welcome.isDisplayed(),
                "Welcome message should be visible");

        System.out.println("Welcome text: " + welcome.getText());
        System.out.println("✅ testDashboardLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — All 3 feature cards are visible
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "All 3 feature cards should be visible")
    public void testFeatureCardsVisible() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Check Browse Books card
        WebElement booksCard = driver.findElement(
                By.xpath("//h4[contains(text(),'Browse Books')]"));
        Assert.assertTrue(booksCard.isDisplayed(),
                "Browse Books card should be visible");

        // Check Your Cart card
        WebElement cartCard = driver.findElement(
                By.xpath("//h4[contains(text(),'Your Cart')]"));
        Assert.assertTrue(cartCard.isDisplayed(),
                "Your Cart card should be visible");

        // Check Blogs card
        WebElement blogsCard = driver.findElement(
                By.xpath("//h4[contains(text(),'Blogs')]"));
        Assert.assertTrue(blogsCard.isDisplayed(),
                "Blogs card should be visible");

        System.out.println("✅ testFeatureCardsVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Go to Books button navigates correctly
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Go to Books button should navigate to books page")
    public void testGoToBooksNavigation() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Click Go to Books button
        WebElement goBooksBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Go to Books')]"));
        goBooksBtn.click();
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/books"),
                "Should navigate to books page");

        System.out.println("✅ testGoToBooksNavigation PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Go to Cart button navigates correctly
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Go to Cart button should navigate to cart page")
    public void testGoToCartNavigation() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Click Go to Cart button
        WebElement goCartBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Go to Cart')]"));
        goCartBtn.click();
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/cart"),
                "Should navigate to cart page");

        System.out.println("✅ testGoToCartNavigation PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Go to Blogs button navigates correctly
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Go to Blogs button should navigate to blogs page")
    public void testGoToBlogsNavigation() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Click Go to Blogs button
        WebElement goBlogsBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Go to Blogs')]"));
        goBlogsBtn.click();
        Thread.sleep(2000);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/blogs"),
                "Should navigate to blogs page");

        System.out.println("✅ testGoToBlogsNavigation PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Admin badge visible for admin user
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Admin badge should be visible for admin user")
    public void testAdminBadgeVisible() throws Exception {

        // Login as admin
        loginAsAdmin();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Check Admin Access badge is visible
        WebElement adminBadge = driver.findElement(
                By.xpath("//span[contains(text(),'Admin Access')]"));
        Assert.assertTrue(adminBadge.isDisplayed(),
                "Admin badge should be visible for admin user");

        System.out.println("✅ testAdminBadgeVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 6 — Admin badge NOT visible for regular user
    // ─────────────────────────────────────────
    @Test(priority = 6, description = "Admin badge should NOT be visible for regular user")
    public void testAdminBadgeNotVisibleForUser() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/dashboard");
        Thread.sleep(2000);

        // Admin badge should not exist for regular user
        java.util.List<WebElement> adminBadge = driver.findElements(
                By.xpath("//span[contains(text(),'Admin Access')]"));

        Assert.assertEquals(adminBadge.size(), 0,
                "Admin badge should NOT be visible for regular user");

        System.out.println("✅ testAdminBadgeNotVisibleForUser PASSED");
    }
}