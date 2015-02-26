package com.bootstrap.utils;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public final class ColorUtils {
  private final static float R_LUM = 0.213f; // 0.3086f
  private final static float G_LUM = 0.715f; // 0.6094f
  private final static float B_LUM = 0.072f; // 0.0820f

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
      // Monochrome
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

  public static ColorMatrixColorFilter modifySaturation(final float amount) {
    final ColorMatrix colorMatrix = new ColorMatrix();
    colorMatrix.setSaturation(amount);
    return new ColorMatrixColorFilter(colorMatrix);
  }

  public static ColorMatrixColorFilter tint(final float r, final float g, final float b) {
    final ColorMatrix colorMatrix = new ColorMatrix();
    final ColorMatrix colorScale = new ColorMatrix();
    colorScale.setScale(r, g, b, 1.0f);
    colorMatrix.postConcat(colorScale);
    return new ColorMatrixColorFilter(colorMatrix);
  }

  public static void alpha(final float v, final ColorMatrix matrix) {
    if (v < 0.0f || v > 1.f) {
      throw new RuntimeException("Alpha has to be in range [0, 1.0]");
    }
    final float[] array = matrix.getArray();
    array[18] = v;
  }

  public static ColorMatrix brightness(float v) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Brightness has to be in range [-1.0, 1.0]");
    }
    final boolean negative = v < 0.f;
    v = Math.abs(v);
    final float n = v * v * v * 9.f + 1.f;
    final float t = negative ? -n * 204.f * v : 0.f;
    // @formatter:off
    return new ColorMatrix(new float[]{
      n, 0.f, 0.f, 0.f, t,
      0.f, n, 0.f, 0.f, t,
      0.f, 0.f, n, 0.f, t,
      0.f, 0.f, 0.f, 1.f, 0.f
    });
    // @formatter:on
  }

  public static ColorMatrix lightness(final float v) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Lightness has to be in range [-1.0, 1.0]");
    }
    final float t = v * 255.f;
    return new ColorMatrix(new float[]{
      1.f, 0.f, 0.f, 0.f, t,
      0.f, 1.f, 0.f, 0.f, t,
      0.f, 0.f, 1.f, 0.f, t,
      0.f, 0.f, 0.f, 1.f, 0.f
    });
  }

  public static ColorMatrix contrast(final float v) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Contrast has to be in range [-1.0, 1.0]");
    }
    final float n = v + 1.f;
    final float t = 128.f * (1.f - n);
    // @formatter:off
    final float[] matrix = new float[]{
      n, 0.f, 0.f, 0.f, t,
      0.f, n, 0.f, 0.f, t,
      0.f, 0.f, n, 0.f, t,
      0.f, 0.f, 0.f, 1.f, 0.f
    };
    // @formatter:on
    return new ColorMatrix(matrix);
  }

  public static void contrast(final float v, final ColorMatrix matrix) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Contrast has to be in range [-1.0, 1.0]");
    }
    final float n = v + 1.f;
    final float t = 128.f * (1.f - n);
    final float[] array = matrix.getArray();
    array[0] = n;
    array[4] = t;
    array[6] = n;
    array[9] = t;
    array[12] = n;
    array[14] = t;
  }

  public static ColorMatrix levels(final int channel, final float v) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Level has to be in range [-1.0, 1.0]");
    }
    final float r = (channel == 0 || channel == 3) ? 1.f + v * 1.f : 1.f;
    final float g = (channel == 1 || channel == 3) ? 1.f + v * 1.f : 1.f;
    final float b = (channel == 2 || channel == 3) ? 1.f + v * 1.f : 1.f;
    // @formatter:off
    final float[] matrix = new float[]{
      r, 0.f, 0.f, 0.f, 0.f,
      0.f, g, 0.f, 0.f, 0.f,
      0.f, 0.f, b, 0.f, 0.f,
      0.f, 0.f, 0.f, 1.f, 0.f
    };
    // @formatter:on
    return new ColorMatrix(matrix);
  }

  public static ColorMatrix saturation(final float v) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Saturation has to be in range [-1.0, 1.0]");
    }
    final float n = 1.f + (v > 0.f ? 3.f * v : v);
    final float r = (1.f - n) * R_LUM;
    final float g = (1.f - n) * G_LUM;
    final float b = (1.f - n) * B_LUM;
    // @formatter:off
    final float[] matrix = new float[]{
      r + n, g, b, 0.f, 0.f,
      r, g + n, b, 0.f, 0.f,
      r, g, b + n, 0.f, 0.f,
      0.f, 0.f, 0.f, 1.f, 0.f
    };
    // @formatter:on
    return new ColorMatrix(matrix);
  }

  public static void saturation(final float v, final ColorMatrix matrix) {
    if (v < -1.f || v > 1.f) {
      throw new RuntimeException("Saturation has to be in range [-1.0, 1.0]");
    }
    final float n = 1.f + (v > 0.f ? 3.f * v : v);
    final float r = (1.f - n) * R_LUM;
    final float g = (1.f - n) * G_LUM;
    final float b = (1.f - n) * B_LUM;
    final float[] array = matrix.getArray();
    array[0] = r + n;
    array[1] = g;
    array[2] = b;
    array[5] = r;
    array[6] = g + n;
    array[7] = b;
    array[7] = b;
    array[10] = r;
    array[11] = g;
    array[12] = b + n;
  }

  public static ColorMatrix colorize(final int color, final int intensity) {
    final float p = intensity / 100.f;
    final float up = Math.abs(p);

    float r = Color.red(color) / 255.f;
    float g = Color.green(color) / 255.f;
    float b = Color.blue(color) / 255.f;
    if (p < 0.f) {
      r = 1.f - r;
      g = 1.f - g;
      b = 1.f - b;
    }

    final float irl = 1.f - (1.f - R_LUM) * up;
    final float igl = 1.f - (1.f - G_LUM) * up;
    final float ibl = 1.f - (1.f - B_LUM) * up;

    final float rl = R_LUM * up;
    final float gl = G_LUM * up;
    final float bl = B_LUM * up;

    // @formatter:off
    final float[] matrix = new float[]{
      irl, gl, bl, 0.f, r * p * 255.f,
      rl, igl, bl, 0.f, g * p * 255.f,
      rl, gl, ibl, 0.f, b * p * 255.f,
      0.f, 0.f, 0.f, 1.f, 0.f
    };
    // @formatter:on
    return new ColorMatrix(matrix);
  }

  public static ColorMatrix multiply(final int color) {
    final float r = Color.red(color) / 255.f;
    final float g = Color.green(color) / 255.f;
    final float b = Color.blue(color) / 255.f;
    // @formatter:off
    return new ColorMatrix(new float[]{
      r, 0.f, 0.f, 0.f, 0.f,
      0.f, g, 0.f, 0.f, 0.f,
      0.f, 0.f, b, 0.f, 0.f,
      0.f, 0.f, 0.f, 1.f, 0.f
    });
    // @formatter:on
  }

  public static ColorMatrix dim(final float v) {
    if (v < 0.f || v > 1.f) {
      throw new RuntimeException("Dim has to be in range [0, 1.0]");
    }
    final float n = 1.f - (1.f * v);
    // @formatter:off
    return new ColorMatrix(new float[]{
      n, 0.f, 0.f, 0.f, 0.f,
      0.f, n, 0.f, 0.f, 0.f,
      0.f, 0.f, n, 0.f, 0.f,
      0.f, 0.f, 0.f, 1.f, 0.f
    });
    // @formatter:on
  }
}
