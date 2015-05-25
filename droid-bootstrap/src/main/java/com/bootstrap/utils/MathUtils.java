package com.bootstrap.utils;

public final class MathUtils {
  private MathUtils() {
  }

  public static int clamp(final int value, final int min, final int max) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
  }

  public static float clamp(final float value, final float min, final float max) {
    if (value < min) return min;
    if (value > max) return max;
    return value;
  }
}
