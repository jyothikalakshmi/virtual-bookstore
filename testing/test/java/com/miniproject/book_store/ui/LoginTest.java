package com.miniproject.book_store.ui;

import com.miniproject.book_store.base.UiBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest extends UiBase {

    // ─────────────────────────────────────────
    // TEST 0 — Login page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Login page should load correctly")
    public void testLoginPageLoads() {

        driver.get(FRONTEND_URL + "/login");

        // Check page title
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);

        // Check email input is present
        WebElement emailInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter email']"));
        Assert.assertTrue(emailInput.isDisplayed(),
                "Email input should be visible");

        // Check password input is present
        WebElement passwordInput = driver.findElement(
                By.cssSelector("input[placeholder='Enter password']"));
        Assert.assertTrue(passwordInput.isDisplayed(),
                "Password input should be visible");

        // Check login button is present
        WebElement loginBtn = driver.findElement(
                By.cssSelector("button.btn-primary"));
        Assert.assertTrue(loginBtn.isDisplayed(),
                "Login button should be visible");

        System.out.println("✅ testLoginPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Login with valid credentials
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Login with valid credentials")
    public void testLoginSuccess() {

        driver.get(FRONTEND_URL + "/login");

        // Type email
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter email']"))
                .sendKeys("test.gmail.com");

        // Type password
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter password']"))
                .sendKeys("123");

        // Click login button
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();

        // Wait for page to navigate to dashboard
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        // Verify we are on dashboard page
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/dashboard"),
                "Should navigate to dashboard after login");

        System.out.println("✅ testLoginSuccess PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Login with wrong password
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Login with wrong password should show error")
    public void testLoginWrongPassword() {

        driver.get(FRONTEND_URL + "/login");

        // Type email
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter email']"))
                .sendKeys("test.gmail.com");

        // Type wrong password
        driver.findElement(
                        By.cssSelector("input[placeholder='Enter password']"))
                .sendKeys("wrongpassword");

        // Click login button
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();

        // Wait for error message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert-danger")));

        // Verify error message is shown
        WebElement errorMsg = driver.findElement(
                By.cssSelector(".alert-danger"));
        Assert.assertTrue(errorMsg.isDisplayed(),
                "Error message should be displayed");

        System.out.println("Error message: " + errorMsg.getText());
        System.out.println("✅ testLoginWrongPassword PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Login with empty fields
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Login with empty fields should show error")
    public void testLoginEmptyFields() {

        driver.get(FRONTEND_URL + "/login");

        // Click login without filling anything
        driver.findElement(
                        By.cssSelector("button.btn-primary"))
                .click();

        // Should stay on login page — not navigate away
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/login"),
                "Should stay on login page when fields are empty");

        System.out.println("✅ testLoginEmptyFields PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Navigate to register page
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Click register link should go to register page")
    public void testNavigateToRegister() {

        driver.get(FRONTEND_URL + "/login");

        // Click "Register here" link
        driver.findElement(By.linkText("Register here"))
                .click();

        // Wait for navigation
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/register"));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/register"),
                "Should navigate to register page");

        System.out.println("✅ testNavigateToRegister PASSED");
    }
}