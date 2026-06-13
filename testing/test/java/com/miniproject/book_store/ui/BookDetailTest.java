package com.miniproject.book_store.ui;

import com.miniproject.book_store.base.UiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class BookDetailTest extends UiBase {

    // Book ID 1 — springboot book which exists in your DB
    private static final String BOOK_DETAIL_URL = "/books/1";

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
    // TEST 0 — Book detail page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Book detail page should load correctly")
    public void testBookDetailPageLoads() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        // Check book name is visible
        WebElement bookName = driver.findElement(
                By.xpath("//h2[contains(text(),'springboot')]"));
        Assert.assertTrue(bookName.isDisplayed(),
                "Book name should be visible");

        System.out.println("Book name: " + bookName.getText());
        System.out.println("✅ testBookDetailPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Book details are displayed correctly
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Book details should be displayed correctly")
    public void testBookDetailsDisplayed() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        // Check author
        WebElement author = driver.findElement(
                By.xpath("//p[contains(text(),'by xyz')]"));
        Assert.assertTrue(author.isDisplayed(),
                "Author should be visible");

        // Check price
        WebElement price = driver.findElement(
                By.xpath("//*[contains(text(),'Rs4000')]"));
        Assert.assertTrue(price.isDisplayed(),
                "Price should be visible");

        // Check stock
        WebElement stock = driver.findElement(
                By.xpath("//*[contains(text(),'Stock available')]"));
        Assert.assertTrue(stock.isDisplayed(),
                "Stock info should be visible");

        System.out.println("✅ testBookDetailsDisplayed PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Add to cart button is visible
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Add to cart button should be visible")
    public void testAddToCartButtonVisible() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        WebElement addToCartBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Add to cart')]"));
        Assert.assertTrue(addToCartBtn.isDisplayed(),
                "Add to cart button should be visible");

        System.out.println("✅ testAddToCartButtonVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Click Add to cart shows alert
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Clicking Add to cart should show success alert")
    public void testAddToCartShowsAlert() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        // Click Add to cart
        WebElement addToCartBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Add to cart')]"));
        addToCartBtn.click();
        Thread.sleep(1500);

        // Check alert appears
        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert text: " + alertText);

        Assert.assertTrue(alertText.contains("Added to cart"),
                "Alert should say Added to cart");

        // Dismiss alert
        driver.switchTo().alert().accept();

        System.out.println("✅ testAddToCartShowsAlert PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Customer Reviews section visible
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Customer Reviews section should be visible")
    public void testReviewsSectionVisible() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        // Check Customer Reviews heading
        WebElement reviewsHeading = driver.findElement(
                By.xpath("//h4[contains(text(),'Customer Reviews')]"));
        Assert.assertTrue(reviewsHeading.isDisplayed(),
                "Customer Reviews heading should be visible");

        System.out.println("✅ testReviewsSectionVisible PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Write a Review section visible
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Write a Review section should be visible")
    public void testWriteReviewSectionVisible() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + BOOK_DETAIL_URL);
        Thread.sleep(2000);

        // Check Write a Review heading
        WebElement writeReviewHeading = driver.findElement(
                By.xpath("//h5[contains(text(),'Write a Review')]"));
        Assert.assertTrue(writeReviewHeading.isDisplayed(),
                "Write a Review heading should be visible");

        // Check review textarea
        WebElement reviewTextarea = driver.findElement(
                By.cssSelector("textarea"));
        Assert.assertTrue(reviewTextarea.isDisplayed(),
                "Review textarea should be visible");

        // Check Submit Review button
        WebElement submitBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Submit Review')]"));
        Assert.assertTrue(submitBtn.isDisplayed(),
                "Submit Review button should be visible");

        System.out.println("✅ testWriteReviewSectionVisible PASSED");
    }
}