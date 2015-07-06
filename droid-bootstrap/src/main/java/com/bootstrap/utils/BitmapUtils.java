package com.bootstrap.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class BitmapUtils {
  private BitmapUtils() {
  }

  public static Bitmap crop(final Bitmap src,
                            final int w,
                            final int h,
                            final float horizontalCenterPercent,
                            final float verticalCenterPercent) {
    if (horizontalCenterPercent < 0
      || horizontalCenterPercent > 1
      || verticalCenterPercent < 0
      || verticalCenterPercent > 1) {
      throw new IllegalArgumentException(
        "horizontalCenterPercent and verticalCenterPercent must be between 0.0f and "
          + "1.0f, inclusive.");
    }
    final int srcWidth = src.getWidth();
    final int srcHeight = src.getHeight();
    // exit early if no resize/crop needed
    if (w == srcWidth && h == srcHeight) {
      return src;
    }
    final Matrix matrix = new Matrix();
    final float scale = Math.max((float) w / srcWidth, (float) h / srcHeight);
    matrix.setScale(scale, scale);
    final int srcCroppedW, srcCroppedH;
    int srcX, srcY;
    srcCroppedW = Math.round(w / scale);
    srcCroppedH = Math.round(h / scale);
    srcX = (int) (srcWidth * horizontalCenterPercent - srcCroppedW / 2);
    srcY = (int) (srcHeight * verticalCenterPercent - srcCroppedH / 2);
    // Nudge srcX and srcY to be within the bounds of src
    srcX = Math.max(Math.min(srcX, srcWidth - srcCroppedW), 0);
    srcY = Math.max(Math.min(srcY, srcHeight - srcCroppedH), 0);
    return Bitmap.createBitmap(src, srcX, srcY, srcCroppedW, srcCroppedH, matrix, true);
  }
}
