package com.bootstrap.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

public final class TintedNinePatchDrawable extends Drawable {
  private int tint;
  private int alpha;
  private NinePatchDrawable innerDrawable;

  @SuppressWarnings("deprecation") public TintedNinePatchDrawable(final Resources res, final int resId, final int tint) {
    this.tint = tint;
    this.alpha = Color.alpha(tint);
    this.innerDrawable = (NinePatchDrawable) res.getDrawable(resId);
  }

  public void setTint(final int tint) {
    this.tint = tint;
    this.alpha = Color.alpha(tint);
  }

  @Override public void draw(final Canvas canvas) {
    final Paint paint = innerDrawable.getPaint();
    if (paint.getColorFilter() == null) {
      paint.setColorFilter(new LightingColorFilter(tint, 0));
      paint.setAlpha(alpha);
    }
    innerDrawable.draw(canvas);
  }

  @Override protected void onBoundsChange(final Rect bounds) {
    super.onBoundsChange(bounds);
    innerDrawable.setBounds(bounds);
  }

  @Override public int getOpacity() {
    return 0;
  }

  @Override public void setAlpha(final int alpha) {
    // not used
  }

  @Override public void setColorFilter(final ColorFilter cf) {
    // not used
  }
}
