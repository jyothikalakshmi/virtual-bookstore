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

public class BooksPageTest extends UiBase {

    // ─────────────────────────────────────────
    // TEST 0 — Books page loads correctly
    // ─────────────────────────────────────────
    @Test(priority = 0, description = "Books page should load correctly")
    public void testBooksPageLoads() throws Exception {

        driver.get(FRONTEND_URL + "/books");

        // Wait for books to load
        Thread.sleep(2000);

        // Check page heading "All Books" is visible
        WebElement heading = driver.findElement(
                By.xpath("//h4[contains(text(),'All Books')]"));
        Assert.assertTrue(heading.isDisplayed(),
                "All Books heading should be visible");

        // Check search input is visible
        WebElement searchInput = driver.findElement(
                By.cssSelector("input[placeholder='Search by title or author...']"));
        Assert.assertTrue(searchInput.isDisplayed(),
                "Search input should be visible");

        System.out.println("✅ testBooksPageLoads PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 1 — Books are displayed on page
    // ─────────────────────────────────────────
    @Test(priority = 1, description = "Books should be displayed on page")
    public void testBooksAreDisplayed() throws Exception {

        driver.get(FRONTEND_URL + "/books");

        // Wait for books to load
        Thread.sleep(2000);

        // Check at least one book card is visible
        List<WebElement> bookCards = driver.findElements(
                By.cssSelector(".card"));
        Assert.assertTrue(bookCards.size() > 0,
                "At least one book should be displayed");

        System.out.println("Total books displayed: " + bookCards.size());
        System.out.println("✅ testBooksAreDisplayed PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 2 — Search by title
    // ─────────────────────────────────────────
    @Test(priority = 2, description = "Search by title should filter books")
    public void testSearchByTitle() throws Exception {

        driver.get(FRONTEND_URL + "/books");
        Thread.sleep(2000);

        // Type in search box
        WebElement searchInput = driver.findElement(
                By.cssSelector("input[placeholder='Search by title or author...']"));
        searchInput.sendKeys("springboot");

        Thread.sleep(1500);

        // Check results — should show filtered books
        List<WebElement> bookCards = driver.findElements(
                By.cssSelector(".card"));
        System.out.println("Books after search: " + bookCards.size());

        Assert.assertTrue(bookCards.size() > 0,
                "Search should return at least one result");

        System.out.println("✅ testSearchByTitle PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 3 — Search with no results
    // ─────────────────────────────────────────
    @Test(priority = 3, description = "Search with no match should show no books")
    public void testSearchNoResults() throws Exception {

        driver.get(FRONTEND_URL + "/books");
        Thread.sleep(2000);

        // Type something that doesn't exist
        WebElement searchInput = driver.findElement(
                By.cssSelector("input[placeholder='Search by title or author...']"));
        searchInput.sendKeys("xyzxyzxyz123");

        Thread.sleep(1500);

        // Should show "No books found" message
        WebElement noBooks = driver.findElement(
                By.xpath("//h5[contains(text(),'No books found')]"));
        Assert.assertTrue(noBooks.isDisplayed(),
                "Should show No books found message");

        System.out.println("✅ testSearchNoResults PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 4 — Filter by category
    // ─────────────────────────────────────────
    @Test(priority = 4, description = "Filter by category should show filtered books")
    public void testFilterByCategory() throws Exception {

        driver.get(FRONTEND_URL + "/books");
        Thread.sleep(2000);

        // Click category dropdown
        WebElement categorySelect = driver.findElement(
                By.cssSelector("select.form-select"));
        categorySelect.click();

        // Select "web development" category
        WebElement option = driver.findElement(
                By.xpath("//option[contains(text(),'web development')]"));
        option.click();

        Thread.sleep(1500);

        // Check books are filtered
        List<WebElement> bookCards = driver.findElements(
                By.cssSelector(".card"));
        System.out.println("Books after category filter: " + bookCards.size());

        Assert.assertTrue(bookCards.size() > 0,
                "Category filter should return books");

        System.out.println("✅ testFilterByCategory PASSED");
    }

    // ─────────────────────────────────────────
    // TEST 5 — Click View Details navigates to book detail
    // ─────────────────────────────────────────
    @Test(priority = 5, description = "Click View Details should navigate to book detail page")
    public void testViewBookDetails() throws Exception {

        driver.get(FRONTEND_URL + "/books");
        Thread.sleep(2000);

        // Click first "View Details" button
        WebElement viewDetailsBtn = driver.findElement(
                By.xpath("//button[contains(text(),'View Details')]"));
        viewDetailsBtn.click();

        Thread.sleep(2000);

        // Should navigate to /books/{id}
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/books/"),
                "Should navigate to book detail page");

        System.out.println("✅ testViewBookDetails PASSED");
    }
}