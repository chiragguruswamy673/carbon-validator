package org.example.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class PerformanceUtils {
    public static double getPageSizeKB(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object totalBytes = js.executeScript(
                "const entries = performance.getEntriesByType('resource');" +
                        "return entries.reduce((sum, r) => sum + (r.transferSize || 0), 0);"
        );
        return Double.parseDouble(totalBytes.toString()) / 1024.0; // KB
    }
}