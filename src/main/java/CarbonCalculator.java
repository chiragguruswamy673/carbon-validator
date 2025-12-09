package org.example.utils;

public class CarbonCalculator {
    public static double estimateGramsCO2(double pageSizeKB, long loadTimeMs) {
        double networkComponent = pageSizeKB * 0.0002;
        double timeComponent = (loadTimeMs / 1000.0) * 0.0001;
        return Math.round((networkComponent + timeComponent) * 10000.0) / 10000.0;
    }
}