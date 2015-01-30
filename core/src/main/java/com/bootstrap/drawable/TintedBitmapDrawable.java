package com.bootstrap.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.BitmapDrawable;

public final class TintedBitmapDrawable extends BitmapDrawable {
  private int tint;

  public TintedBitmapDrawable(final Resources res, final Bitmap bitmap, final int tint) {
    super(res, bitmap);
    this.tint = tint;
  }

  public void setTint(final int tint) {
    this.tint = tint;
  }

  @Override public void draw(final Canvas canvas) {
    if (getPaint().getColorFilter() == null) {
      getPaint().setColorFilter(new LightingColorFilter(tint, 0));
    }
    super.draw(canvas);
  }
}
