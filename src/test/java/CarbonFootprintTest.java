package org.example;

import org.example.utils.PerformanceUtils;
import org.example.utils.CarbonCalculator;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // add this import
import org.testng.Assert;
import org.testng.annotations.*;

public class CarbonFootprintTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");             // required in CI
        options.addArguments("--no-sandbox");               // avoids sandbox issues
        options.addArguments("--disable-dev-shm-usage");    // fixes shared memory crashes

        driver = new ChromeDriver(options);
    }

    @Test
    public void validateCarbonFootprint() {
        driver.get("https://www.demoblaze.com");

        long start = System.currentTimeMillis();
        driver.findElement(By.tagName("body"));
        long end = System.currentTimeMillis();

        long loadTimeMs = end - start;
        double pageSizeKB = PerformanceUtils.getPageSizeKB(driver);
        double gramsCO2 = CarbonCalculator.estimateGramsCO2(pageSizeKB, loadTimeMs);

        System.out.println("Page size: " + pageSizeKB + " KB");
        System.out.println("Load time: " + loadTimeMs + " ms");
        System.out.println("Estimated CO₂ per visit: " + gramsCO2 + " g");

        Assert.assertTrue(gramsCO2 < 2.0, "Page emits too much CO₂ per visit!");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }
}