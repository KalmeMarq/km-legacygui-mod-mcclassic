package me.kalmemarq.legacygui.util;

public class MathHelper {
    public static int clamp(int value, int min, int max) {
        return Math.min(max, Math.max(min, value));
    }

    public static float clamp(float value, float min, float max) {
        return Math.min(max, Math.max(min, value));
    }

    public static double clamp(double value, double min, double max) {
        return Math.min(max, Math.max(min, value));
    }
}
