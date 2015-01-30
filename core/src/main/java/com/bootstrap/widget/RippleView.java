package com.bootstrap.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.R;
import com.bootstrap.animation.AbstractAnimatorListener;

public class RippleView extends View {
  private float centerX, centerY, radius;
  private float animValue;
  private long startDelay, duration;
  private boolean withAlpha;
  private boolean revert;
  private Paint paint;
  private Runnable endAction;

  public RippleView(final Context context) {
    super(context);
    init(null);
  }

  public RippleView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public RippleView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public RippleView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(final AttributeSet attrs) {
    int color = Color.WHITE;
    if (attrs != null) {
      final TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RippleView);
      color = array.getColor(R.styleable.RippleView_bgColor, color);
      array.recycle();
    }
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setColor(color);
  }

  public void setBgColor(final int color) {
    paint.setColor(color);
  }

  public RippleView withAlpha() {
    withAlpha = true;
    return this;
  }

  public RippleView startDelay(final long delay) {
    startDelay = delay;
    return this;
  }

  public RippleView duration(final long duration) {
    this.duration = duration;
    return this;
  }

  public RippleView endAction(final Runnable runnable) {
    this.endAction = runnable;
    return this;
  }

  public RippleView revert(final boolean revert) {
    this.revert = revert;
    return this;
  }

  public void ripple(final float centerX, final float centerY, final float maxRadius) {
    final Rect rect = new Rect();
    getGlobalVisibleRect(rect);
    this.centerX = centerX - rect.left;
    this.centerY = centerY - rect.top;

    final ValueAnimator animator = ValueAnimator.ofFloat(revert ? 1.0f : 0.0f, revert ? 0.0f : 1.0f);
    animator.setDuration(duration);
    animator.setStartDelay(startDelay);
    animator.setInterpolator(revert ? new AccelerateInterpolator() : new DecelerateInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(final ValueAnimator animation) {
        animValue = (float) animation.getAnimatedValue();
        radius = maxRadius * animValue;
        if (withAlpha) {
          setAlpha(animValue);
        }
        invalidate();
      }
    });

    if (endAction != null) {
      animator.addListener(new AbstractAnimatorListener() {
        @Override public void onAnimationEnd(final Animator animation) {
          endAction.run();
        }
      });
    }

    animator.start();
  }

  @Override protected void onDraw(final Canvas canvas) {
    canvas.drawCircle(centerX, centerY, radius, paint);
  }
}
