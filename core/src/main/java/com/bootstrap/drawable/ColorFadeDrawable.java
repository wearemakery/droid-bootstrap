package com.bootstrap.drawable;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.utils.ColorUtils;

public final class ColorFadeDrawable extends Drawable {
  private final int colorFrom;
  private final int colorTo;
  private final Paint paint;

  private long startDelay;

  public ColorFadeDrawable(final int colorFrom, final int colorTo) {
    this.colorFrom = colorFrom;
    this.colorTo = colorTo;
    this.paint = new Paint();
    paint.setColor(colorFrom);
  }

  public ColorFadeDrawable fadeIn(final long duration) {
    fade(duration, false);
    return this;
  }

  public ColorFadeDrawable fadeOut(final long duration) {
    fade(duration, true);
    return this;
  }

  public ColorFadeDrawable startDelay(final long startDelay) {
    this.startDelay = startDelay;
    return this;
  }

  private void fade(final long duration, final boolean exit) {
    final ValueAnimator animator = ValueAnimator.ofInt(0);
    animator.setDuration(duration);
    animator.setInterpolator(exit ? new DecelerateInterpolator() : new AccelerateInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(final ValueAnimator animation) {
        paint.setColor(ColorUtils.crossFade(animation.getAnimatedFraction(),
          exit ? colorFrom : colorTo, exit ? colorTo : colorFrom));
        invalidateSelf();
      }
    });
    animator.setStartDelay(startDelay);
    animator.start();
  }

  @Override public void draw(final Canvas canvas) {
    canvas.drawPaint(paint);
  }

  @Override public void setAlpha(final int alpha) {
    // not used
  }

  @Override public void setColorFilter(final ColorFilter cf) {
    // not used
  }

  @Override public int getOpacity() {
    return 0;
  }
}
