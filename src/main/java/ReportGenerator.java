package org.example.report;

import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;

public class ReportGenerator {

    public static void savePerUrl(String url, double pageSizeKB, long loadTimeMs, double gramsCO2) {
        ensureDir("target/reports");
        String host = hostOf(url);
        saveJson(host, url, pageSizeKB, loadTimeMs, gramsCO2);
        saveHtml(host, url, pageSizeKB, loadTimeMs, gramsCO2);
    }

    private static void saveJson(String host, String url, double pageSizeKB, long loadTimeMs, double gramsCO2) {
        JSONObject report = new JSONObject();
        report.put("url", url);
        report.put("host", host);
        report.put("pageSizeKB", pageSizeKB);
        report.put("loadTimeMs", loadTimeMs);
        report.put("gramsCO2", gramsCO2);
        writeFile("target/reports/" + host + ".json", report.toString(4));
    }

    private static void saveHtml(String host, String url, double pageSizeKB, long loadTimeMs, double gramsCO2) {
        String html = """
                <!doctype html>
                <html lang="en">
                  <head>
                    <meta charset="UTF-8">
                    <title>Carbon Report - %s</title>
                    <style>
                      body { font-family: Arial, sans-serif; margin: 24px; }
                      .card { border: 1px solid #ddd; border-radius: 8px; padding: 16px; max-width: 720px; }
                      h1 { font-size: 22px; margin: 0 0 12px; }
                      .row { margin: 8px 0; }
                      .label { font-weight: bold; width: 220px; display: inline-block; }
                      .value { color: #333; }
                      .ok { color: #0b7; }
                      .bad { color: #d33; }
                    </style>
                  </head>
                  <body>
                    <div class="card">
                      <h1>Carbon Footprint Report</h1>
                      <div class="row"><span class="label">Host:</span><span class="value">%s</span></div>
                      <div class="row"><span class="label">URL:</span><span class="value">%s</span></div>
                      <div class="row"><span class="label">Page size:</span><span class="value">%.2f KB</span></div>
                      <div class="row"><span class="label">Load time:</span><span class="value">%d ms</span></div>
                      <div class="row"><span class="label">Estimated CO₂ per visit:</span><span class="value">%.4f g</span></div>
                    </div>
                  </body>
                </html>
                """.formatted(host, host, url, pageSizeKB, loadTimeMs, gramsCO2);
        writeFile("target/reports/" + host + ".html", html);
    }

    private static String hostOf(String url) {
        try {
            return new URI(url).getHost().replaceFirst("^www\\.", "");
        } catch (Exception e) {
            return url.replaceAll("[^a-zA-Z0-9.-]", "_");
        }
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
}