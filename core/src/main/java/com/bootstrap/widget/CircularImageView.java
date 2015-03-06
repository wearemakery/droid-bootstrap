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
  private boolean isAttached;
  private float radius;
  private RectF rect;
  private RectF rectIcon;
  private Paint paint;
  private Bitmap iconBitmap;
  private Runnable action;

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

  public void setIcon(final Bitmap bitmap) {
    if (iconBitmap != bitmap) {
      iconBitmap = bitmap;
      centerIcon();
    }
    if (isAttached) {
      invalidate();
    }
  }

  public void reset() {
    iconBitmap = null;
    paint.setShader(null);
    if (isAttached) {
      invalidate();
    }
  }

  public void reset(final Bitmap bitmap) {
    if (iconBitmap != bitmap) {
      iconBitmap = bitmap;
      centerIcon();
    }
    paint.setShader(null);
    if (isAttached) {
      invalidate();
    }
  }

  public void setLoadedAction(final Runnable action) {
    this.action = action;
  }

  private void init() {
    rect = new RectF();
    rectIcon = new RectF();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(Color.TRANSPARENT);
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    rect.set(0.0f, 0.0f, w, h);
    centerIcon();
    radius = Math.min(w, h) / 2.0f;
  }

  @Override protected void onDraw(final Canvas canvas) {
    canvas.drawCircle(rect.centerX(), rect.centerY(), radius, paint);
    if (iconBitmap != null) {
      canvas.drawBitmap(iconBitmap, rectIcon.left, rectIcon.top, null);
    }
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    isAttached = true;
  }

  @Override protected void onDetachedFromWindow() {
    isAttached = false;
    super.onDetachedFromWindow();
  }

  @Override public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
    iconBitmap = null;
    paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    invalidate();
    if (action != null) {
      action.run();
      action = null;
    }
  }

  @Override public void onBitmapFailed(final Drawable errorDrawable) {
  }

  @Override public void onPrepareLoad(final Drawable placeHolderDrawable) {
    reset();
  }

  private void centerIcon() {
    if (iconBitmap != null) {
      rectIcon.set(rect);
      rectIcon.inset((rect.width() - iconBitmap.getWidth()) / 2, (rect.height() - iconBitmap.getHeight()) / 2);
    }
  }
}
