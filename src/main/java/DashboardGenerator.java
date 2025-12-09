package org.example.report;

import java.io.FileWriter;
import java.io.File;
import java.util.List;

public class DashboardGenerator {

    public static void generate(List<SiteResult> results) {
        ensureDir("target/reports");

        StringBuilder rows = new StringBuilder();
        for (SiteResult r : results) {
            String statusClass = r.passed ? "ok" : "bad";
            String statusText = r.passed ? "PASS" : "FAIL";

            rows.append(String.format("""
                <tr>
                  <td>%s</td>
                  <td>%.2f KB</td>
                  <td>%d ms</td>
                  <td>%.4f g</td>
                  <td class="%s">%s</td>
                  <td><a href="%s.html">HTML</a> | <a href="%s.json">JSON</a></td>
                </tr>
            """, r.host, r.pageSizeKB, r.loadTimeMs, r.gramsCO2, statusClass, statusText, r.host, r.host));
        }

        String html = """
    <!doctype html>
    <html lang="en">
      <head>
        <meta charset="UTF-8">
        <title>Carbon Footprint Dashboard</title>
        <style>
          body { font-family: Arial, sans-serif; margin: 24px; }
          table { border-collapse: collapse; width: 100%%; }
          th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
          th { background: #f4f4f4; }
          .ok { color: #0b7; font-weight: bold; }
          .bad { color: #d33; font-weight: bold; }
        </style>
      </head>
      <body>
        <h1>Carbon Footprint Dashboard</h1>
        <table>
          <tr>
            <th>Host</th>
            <th>Page Size</th>
            <th>Load Time</th>
            <th>CO₂ per Visit</th>
            <th>Status</th>
            <th>Reports</th>
          </tr>
          %s
        </table>
      </body>
    </html>
""".formatted(rows.toString());

        writeFile("target/reports/index.html", html);
    }

    private static void ensureDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
    }

    private static void writeFile(String path, String content) {
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper class to hold results
    public static class SiteResult {
        public String host;
        public double pageSizeKB;
        public long loadTimeMs;
        public double gramsCO2;
        public boolean passed;

        public SiteResult(String host, double pageSizeKB, long loadTimeMs, double gramsCO2, boolean passed) {
            this.host = host;
            this.pageSizeKB = pageSizeKB;
            this.loadTimeMs = loadTimeMs;
            this.gramsCO2 = gramsCO2;
            this.passed = passed;
        }
    }
}