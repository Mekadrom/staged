package com.higgs.staged;

public final class StageUtils {
    private StageUtils() { }

    public static double dist(final double x1, final double y1, final double x2, final double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));// + Math.pow((z2 - z1), 2));
    }

    public static double bound(double d, final double min, final double max) {
        if (d < min) {
            d = min;
        }
        if (d > max) {
            d = max;
        }
        return d;
    }
}
