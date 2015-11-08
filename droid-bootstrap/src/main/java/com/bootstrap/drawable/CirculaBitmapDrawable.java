package com.bootstrap.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CirculaBitmapDrawable extends Drawable {
  private final Paint paint;
  private final Path path;

  private BitmapShader bitmapShader;
  private RectF bitmapRect;

  public CirculaBitmapDrawable(final Bitmap bitmap) {
    bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    bitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(bitmapShader);

    path = new Path();
  }

  public CirculaBitmapDrawable(final int color) {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(color);

    path = new Path();
  }

  @Override protected void onBoundsChange(final Rect bounds) {
    if (bitmapShader != null && bitmapRect != null) {
      final Matrix matrix = new Matrix();
      matrix.setRectToRect(bitmapRect, new RectF(bounds), Matrix.ScaleToFit.CENTER);
      bitmapShader.setLocalMatrix(matrix);
    }

    final RectF viewRect = new RectF(0, 0, bounds.width(), bounds.height());
    path.reset();
    path.addCircle(viewRect.centerX(), viewRect.centerY(), Math.min(viewRect.width(), viewRect.height()), Path.Direction.CW);
  }

  @Override public void draw(final Canvas canvas) {
    canvas.drawPath(path, paint);
  }

  @Override public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  @Override public void setAlpha(final int alpha) {
    paint.setAlpha(alpha);
  }

  @Override public void setColorFilter(final ColorFilter cf) {
    paint.setColorFilter(cf);
  }
}
