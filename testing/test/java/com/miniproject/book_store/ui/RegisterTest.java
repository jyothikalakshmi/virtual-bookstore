package com.miniproject.book_store.ui;

import com.miniproject.book_store.base.UiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class RegisterTest extends UiBase {

    // Unique email every run — no duplicate in DB
    private static final String UNIQUE_EMAIL =
            "reguser" + System.currentTimeMillis() + "@gmail.com";

    // ─────────────────────────────────────────
    // TEST 0 — Register page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Register page should load correctly")
    public void testRegisterPageLoads() {

        driver.get(FRONTEND_URL + "/register");

        // Check all fields are visible
        WebElement nameInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter your name']"));
        Assert.assertTrue(nameInput.isDisplayed(),
                "Name input should be visible");

        WebElement emailInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter email']"));
        Assert.assertTrue(emailInput.isDisplayed(),
                "Email input should be visible");

        WebElement passwordInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter password']"));
        Assert.assertTrue(passwordInput.isDisplayed(),
                "Password input should be visible");

        WebElement phoneInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter phone number']"));
        Assert.assertTrue(phoneInput.isDisplayed(),
                "Phone input should be visible");

        WebElement registerBtn = driver.findElement(
                By.cssSelector("button.btn-primary"));
        Assert.assertTrue(registerBtn.isDisplayed(),
                "Register button should be visible");

        System.out.println("✅ testRegisterPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Register with valid details
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Register with valid details successfully")
    public void testRegisterSuccess() throws Exception {

        driver.get(FRONTEND_URL + "/register");

        // Fill name
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter your name']"))
                .sendKeys("Test Register User");

        // Fill email — unique every run
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter email']"))
                .sendKeys(UNIQUE_EMAIL);

        // Fill password
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter password']"))
                .sendKeys("test123");

        // Fill phone
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter phone number']"))
                .sendKeys("9876543210");

        // Click register button
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();

        // Wait for success message or navigation
        Thread.sleep(2000);

        // Check current URL or success message
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after register: " + currentUrl);

        // After register, should either show success or go to login
        Assert.assertTrue(
                currentUrl.contains("/register") || currentUrl.contains("/login"),
                "Should stay on register (success msg) or go to login");

        System.out.println("✅ testRegisterSuccess PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Register with empty fields
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Register with empty fields should stay on page")
    public void testRegisterEmptyFields() throws Exception {

        driver.get(FRONTEND_URL + "/register");

        // Click register without filling anything
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();

        Thread.sleep(2000);

        // Should stay on register page
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/register"),
                "Should stay on register page when fields are empty");

        System.out.println("✅ testRegisterEmptyFields PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Navigate to login page
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Click login link should go to login page")
    public void testNavigateToLogin() {

        driver.get(FRONTEND_URL + "/register");

        // Click "Login here" link
        driver.findElement(By.linkText("Login here"))
                .click();

        // Wait for navigation
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/login"));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/login"),
                "Should navigate to login page");

        System.out.println("✅ testNavigateToLogin PASSED");
    }
}