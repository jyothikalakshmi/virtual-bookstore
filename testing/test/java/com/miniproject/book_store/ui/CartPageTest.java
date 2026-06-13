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

public class CartPageTest extends UiBase {

    // ─────────────────────────────────────────
    // Helper method — Login as user 1
    // ─────────────────────────────────────────
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

        // Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        System.out.println("✅ Logged in successfully");
    }

    // ─────────────────────────────────────────
    // Helper method — Add first book to cart
    // ─────────────────────────────────────────
    private void addBookToCart() throws Exception {

        // Go to books page
        driver.get(FRONTEND_URL + "/books");
        Thread.sleep(2000);

        // Click View Details on first book
        WebElement viewDetailsBtn = driver.findElement(
                By.xpath("//button[contains(text(),'View Details')]"));
        viewDetailsBtn.click();
        Thread.sleep(2000);

        // Click Add to Cart button
        WebElement addToCartBtn = driver.findElement(
                By.xpath("//button[contains(text(),'Add to cart')]"));
        addToCartBtn.click();
        Thread.sleep(2000);


// ✅ Dismiss the "Added to cart successfully!" alert
        driver.switchTo().alert().accept();
        Thread.sleep(1000);

        System.out.println("✅ Book added to cart");
    }

    // ─────────────────────────────────────────
    // TEST 0 — Cart page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Cart page should load correctly")
    public void testCartPageLoads() throws Exception {

        loginAsUser();
        driver.get(FRONTEND_URL + "/cart");
        Thread.sleep(2000);

        // Check heading is visible
        WebElement heading = driver.findElement(
                By.xpath("//h3[contains(text(),'Your Cart')]"));
        Assert.assertTrue(heading.isDisplayed(),
                "Your Cart heading should be visible");

        System.out.println("✅ testCartPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Add book to cart and verify
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Add book to cart and verify it appears")
    public void testAddBookToCart() throws Exception {

        loginAsUser();
        addBookToCart();

        // Now go to cart page
        driver.get(FRONTEND_URL + "/cart");
        Thread.sleep(2000);

        // Verify cart has items
        List<WebElement> cartItems = driver.findElements(
                By.cssSelector(".card.mb-3"));
        System.out.println("Cart items count: " + cartItems.size());

        Assert.assertTrue(cartItems.size() > 0,
                "Cart should have at least one item");

        System.out.println("✅ testAddBookToCart PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Increase quantity
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Increase cart item quantity")
    public void testIncreaseQuantity() throws Exception {

        loginAsUser();
        addBookToCart();

        driver.get(FRONTEND_URL + "/cart");
        Thread.sleep(2000);

        // More specific locator for quantity
        WebElement quantityText = driver.findElement(
                By.cssSelector(".col-md-3 .fw-bold"));
        String beforeQty = quantityText.getText();
        System.out.println("Quantity before: " + beforeQty);

        // Click + button
        WebElement increaseBtn = driver.findElement(
                By.xpath("//button[contains(text(),'+')]"));
        increaseBtn.click();
        Thread.sleep(1500);

        // Check quantity increased
        WebElement updatedQty = driver.findElement(
                By.cssSelector(".col-md-3 .fw-bold"));
        String afterQty = updatedQty.getText();
        System.out.println("Quantity after: " + afterQty);

        Assert.assertNotEquals(beforeQty, afterQty,
                "Quantity should have increased");

        System.out.println("✅ testIncreaseQuantity PASSED");
    }

    @Test(priority = 3, description = "Remove item from cart")
    public void testRemoveCartItem() throws Exception {

        loginAsUser();
        addBookToCart();

        driver.get(FRONTEND_URL + "/cart");
        Thread.sleep(2000);

        // Count before remove
        List<WebElement> beforeItems = driver.findElements(
                By.cssSelector(".card.mb-3"));
        int beforeCount = beforeItems.size();
        System.out.println("Items before remove: " + beforeCount);

        // Print current page source snippet to see what's on page
        System.out.println("Current URL before click: " + driver.getCurrentUrl());

        // Click Remove button using exact text
        WebElement removeBtn = driver.findElement(
                By.xpath("//button[text()='Remove']"));
        System.out.println("Remove button found: " + removeBtn.isDisplayed());
        removeBtn.click();
        System.out.println("Remove button clicked");
        Thread.sleep(3000);

        System.out.println("Current URL after click: " + driver.getCurrentUrl());

        // Count after remove
        List<WebElement> afterItems = driver.findElements(
                By.cssSelector(".card.mb-3"));
        int afterCount = afterItems.size();
        System.out.println("Items after remove: " + afterCount);

        // Check empty cart message
        List<WebElement> emptyMsg = driver.findElements(
                By.xpath("//h5[contains(text(),'Your cart is empty')]"));
        System.out.println("Empty cart message visible: " + emptyMsg.size());

        boolean itemRemoved = afterCount < beforeCount;
        boolean cartEmpty = emptyMsg.size() > 0;

        Assert.assertTrue(itemRemoved || cartEmpty,
                "Item should be removed from cart");

        System.out.println("✅ testRemoveCartItem PASSED");
    }
    // ─────────────────────────────────────────
    // TEST 4 — Place order
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Place order from cart")
    public void testPlaceOrder() throws Exception {

        loginAsUser();
        addBookToCart();

        driver.get(FRONTEND_URL + "/cart");
        Thread.sleep(2000);

        // Click Place Order button
        WebElement placeOrderBtn = driver.findElement(
                By.cssSelector("button.btn-success"));
        placeOrderBtn.click();
        Thread.sleep(3000);

        // Check order success message appears
        WebElement successMsg = driver.findElement(
                By.cssSelector(".alert-success"));
        Assert.assertTrue(successMsg.isDisplayed(),
                "Order success message should be visible");

        System.out.println("Success message: " + successMsg.getText());
        System.out.println("✅ testPlaceOrder PASSED");
    }
}
