package com.bootstrap.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;

public final class TintedBitmapDrawable extends BitmapDrawable {
  private int tint;
  private int alpha;

  public static TintedBitmapDrawable fromColor(final Resources res, final int resId, final int color) {
    return new TintedBitmapDrawable(res, resId, color);
  }

  public static TintedBitmapDrawable fromColor(final Resources res, final Bitmap bitmap, final int color) {
    return new TintedBitmapDrawable(res, bitmap, color);
  }

  public static TintedBitmapDrawable fromColorRes(final Resources res, final int resId, final int colorResId) {
    return new TintedBitmapDrawable(res, resId, res.getColor(colorResId));
  }

  public static TintedBitmapDrawable fromColorRes(final Resources res, final Bitmap bitmap, final int colorResId) {
    return new TintedBitmapDrawable(res, bitmap, res.getColor(colorResId));
  }

  public static StateListDrawable fromEnabledDisabledState(final Resources res, final int resId, final int enabledColorResId, final int disabledColorResId) {
    final StateListDrawable drawable = new StateListDrawable();
    drawable.addState(new int[]{-android.R.attr.state_enabled}, fromColorRes(res, resId, disabledColorResId));
    drawable.addState(new int[]{}, fromColorRes(res, resId, enabledColorResId));
    return drawable;
  }

  public TintedBitmapDrawable(final Resources res, final Bitmap bitmap, final int tint) {
    super(res, bitmap);
    this.tint = tint;
  }

  public TintedBitmapDrawable(final Resources res, final int resId, final int tint) {
    super(res, BitmapFactory.decodeResource(res, resId));
    this.tint = tint;
    this.alpha = Color.alpha(tint);
  }

  public void setTint(final int tint) {
    this.tint = tint;
    this.alpha = Color.alpha(tint);
  }

  @Override public void draw(final Canvas canvas) {
    final Paint paint = getPaint();
    if (paint.getColorFilter() == null) {
      paint.setColorFilter(new LightingColorFilter(tint, 0));
      paint.setAlpha(alpha);
    }
    super.draw(canvas);
  }
}
