package com.higgs.staged;

public class StageUtils {
    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));// + Math.pow((z2 - z1), 2));
    }
}
