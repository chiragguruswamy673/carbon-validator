package org.example;

import org.example.report.ReportGenerator;
import org.example.report.DashboardGenerator;
import org.example.report.DashboardGenerator.SiteResult;
import org.example.utils.CarbonCalculator;
import org.example.utils.PerformanceUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONObject;

public class CarbonFootprintTest {
    private WebDriver driver;
    private double defaultThresholdGrams;
    private Map<String, Double> domainThresholds;
    private List<SiteResult> results = new ArrayList<>();

    @BeforeClass
    public void setup() throws Exception {
        loadThresholds();

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @DataProvider(name = "sites")
    public Object[][] sites() {
        return new Object[][]{
                {"Demoblaze", "https://www.demoblaze.com"},
                {"BBC", "https://www.bbc.com/news"},
                {"NDTV", "https://www.ndtv.com"}
        };
    }

    @Test(dataProvider = "sites")
    public void validateCarbonForSite(String siteName, String url) {
        driver.get(url);

        long start = System.currentTimeMillis();
        driver.findElement(By.tagName("body")); // ensure page rendered
        long loadTimeMs = System.currentTimeMillis() - start;

        double pageSizeKB = PerformanceUtils.getPageSizeKB(driver);
        double gramsCO2 = CarbonCalculator.estimateGramsCO2(pageSizeKB, loadTimeMs);

        // Save per-URL reports
        ReportGenerator.savePerUrl(url, pageSizeKB, loadTimeMs, gramsCO2);

        // Threshold: domain-specific or default
        String host = url.replaceFirst("^https?://(www\\.)?", "").split("/")[0];
        double threshold = domainThresholds.getOrDefault(host, defaultThresholdGrams);

        boolean passed = gramsCO2 < threshold;
        results.add(new SiteResult(host, pageSizeKB, loadTimeMs, gramsCO2, passed));

        System.out.printf("Site=%s Host=%s Size=%.2fKB Load=%dms CO2=%.4fg Threshold=%.2fg%n",
                siteName, host, pageSizeKB, loadTimeMs, gramsCO2, threshold);

        Assert.assertTrue(passed,
                String.format("CO₂ %.4f g exceeds threshold %.2f g for %s", gramsCO2, threshold, siteName));
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        if (driver != null) driver.quit();
        // Generate summary dashboard after all tests
        DashboardGenerator.generate(results);
    }

    private void loadThresholds() throws Exception {
        defaultThresholdGrams = 2.0;
        domainThresholds = new java.util.HashMap<>();

        String path = "src/test/resources/config.json";
        if (!Files.exists(Paths.get(path))) return;

        String json = Files.readString(Paths.get(path));
        JSONObject obj = new JSONObject(json);

        defaultThresholdGrams = obj.optDouble("defaultThresholdGrams", defaultThresholdGrams);

        JSONObject domains = obj.optJSONObject("domainThresholds");
        if (domains != null) {
            for (String key : domains.keySet()) {
                domainThresholds.put(key, domains.getDouble(key));
            }
        }
    }
}