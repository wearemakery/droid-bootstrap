package com.bootstrap.utils;

import android.graphics.Color;

public final class ColorUtils {
  private ColorUtils() {
  }

  public static int compositeColors(final int fg, final int bg) {
    final float alpha1 = Color.alpha(fg) / 255f;
    final float alpha2 = Color.alpha(bg) / 255f;

    float a = (alpha1 + alpha2) * (1f - alpha1);
    float r = (Color.red(fg) * alpha1) + (Color.red(bg) * alpha2 * (1f - alpha1));
    float g = (Color.green(fg) * alpha1) + (Color.green(bg) * alpha2 * (1f - alpha1));
    float b = (Color.blue(fg) * alpha1) + (Color.blue(bg) * alpha2 * (1f - alpha1));

    return Color.argb((int) a, (int) r, (int) g, (int) b);
  }

  public static int crossFade(final float fraction, final int startColor, final int endColor) {
    int startA = (startColor >> 24) & 0xff;
    int startR = (startColor >> 16) & 0xff;
    int startG = (startColor >> 8) & 0xff;
    int startB = startColor & 0xff;

    int endA = (endColor >> 24) & 0xff;
    int endR = (endColor >> 16) & 0xff;
    int endG = (endColor >> 8) & 0xff;
    int endB = endColor & 0xff;

    return (startA + (int) (fraction * (endA - startA))) << 24 |
      (startR + (int) (fraction * (endR - startR))) << 16 |
      (startG + (int) (fraction * (endG - startG))) << 8 |
      (startB + (int) (fraction * (endB - startB)));
  }

  public static double calculateLuminance(final int color) {
    double red = Color.red(color) / 255d;
    red = red < 0.03928 ? red / 12.92 : Math.pow((red + 0.055) / 1.055, 2.4);

    double green = Color.green(color) / 255d;
    green = green < 0.03928 ? green / 12.92 : Math.pow((green + 0.055) / 1.055, 2.4);

    double blue = Color.blue(color) / 255d;
    blue = blue < 0.03928 ? blue / 12.92 : Math.pow((blue + 0.055) / 1.055, 2.4);

    return (0.2126 * red) + (0.7152 * green) + (0.0722 * blue);
  }

  public static float[] RGBtoHSL(final int color) {
    final float rf = Color.red(color) / 255f;
    final float gf = Color.green(color) / 255f;
    final float bf = Color.blue(color) / 255f;

    final float max = Math.max(rf, Math.max(gf, bf));
    final float min = Math.min(rf, Math.min(gf, bf));
    final float deltaMaxMin = max - min;

    float h, s;
    float l = (max + min) / 2f;

    if (max == min) {
      // Monochromatic
      h = s = 0f;
    } else {
      if (max == rf) {
        h = ((gf - bf) / deltaMaxMin) % 6f;
      } else if (max == gf) {
        h = ((bf - rf) / deltaMaxMin) + 2f;
      } else {
        h = ((rf - gf) / deltaMaxMin) + 4f;
      }

      s = deltaMaxMin / (1f - Math.abs(2f * l - 1f));
    }

    return new float[]{(h * 60f) % 360f, s, l};
  }

  public static int HSLtoRGB(final float[] hsl) {
    final float h = hsl[0];
    final float s = hsl[1];
    final float l = hsl[2];

    final float c = (1f - Math.abs(2 * l - 1f)) * s;
    final float m = l - 0.5f * c;
    final float x = c * (1f - Math.abs((h / 60f % 2f) - 1f));

    final int hueSegment = (int) h / 60;

    int r = 0, g = 0, b = 0;

    switch (hueSegment) {
      case 0:
        r = Math.round(255 * (c + m));
        g = Math.round(255 * (x + m));
        b = Math.round(255 * m);
        break;
      case 1:
        r = Math.round(255 * (x + m));
        g = Math.round(255 * (c + m));
        b = Math.round(255 * m);
        break;
      case 2:
        r = Math.round(255 * m);
        g = Math.round(255 * (c + m));
        b = Math.round(255 * (x + m));
        break;
      case 3:
        r = Math.round(255 * m);
        g = Math.round(255 * (x + m));
        b = Math.round(255 * (c + m));
        break;
      case 4:
        r = Math.round(255 * (x + m));
        g = Math.round(255 * m);
        b = Math.round(255 * (c + m));
        break;
      case 5:
      case 6:
        r = Math.round(255 * (c + m));
        g = Math.round(255 * m);
        b = Math.round(255 * (x + m));
        break;
    }

    r = Math.max(0, Math.min(255, r));
    g = Math.max(0, Math.min(255, g));
    b = Math.max(0, Math.min(255, b));

    return Color.rgb(r, g, b);
  }
}
