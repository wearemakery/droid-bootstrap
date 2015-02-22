package com.bootstrap.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class CircularImageView extends View implements Target {
  private float radius;
  private RectF rect;
  private Paint paint;


  public CircularImageView(final Context context) {
    super(context);
    init();
  }

  public CircularImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CircularImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void setPrepareColor(final int color) {
    paint.setColor(color);
  }

  public void reset() {
    paint.setShader(null);
    invalidate();
  }

  private void init() {
    rect = new RectF();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.TRANSPARENT);
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    rect.set(0.0f, 0.0f, w, h);
    radius = Math.min(w, h) / 2.0f;
  }

  @Override protected void onDraw(final Canvas canvas) {
    canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
  }

  @Override public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
    paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    invalidate();
  }

  @Override public void onBitmapFailed(final Drawable errorDrawable) {
  }

  @Override public void onPrepareLoad(final Drawable placeHolderDrawable) {
    reset();
  }
}
