package com.miniproject.book_store.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class UiBase {

    protected static final String FRONTEND_URL = "http://localhost:4200";
    protected WebDriver driver;

    @BeforeMethod
    public void openBrowser() {

        // Selenium 4 built-in manager
        // No WebDriverManager, no manual download needed
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);

        driver.manage().timeouts().implicitlyWait(
                java.time.Duration.ofSeconds(10));
        driver.manage().window().maximize();

        System.out.println("✅ Edge browser opened");
    }

    @AfterMethod
    public void closeBrowser() {
        if (driver != null) {
            try {
                Thread.sleep(2000); // ← waits 2 seconds before closing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("✅ Edge browser closed");
        }
    }
}