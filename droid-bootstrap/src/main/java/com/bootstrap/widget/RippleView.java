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
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.R;
import com.bootstrap.animation.AbstractAnimatorListener;

public class RippleView extends View {
  private float centerX, centerY, maxRadius, radius;
  private float animValue;
  private long startDelay, duration;
  private int repeatCount, repeatMode;
  private int currentLayerType;
  private boolean withAlpha, reverseAlpha;
  private boolean revert;
  private Paint paint;
  private Rect boundsRect;
  private Rect rippleRect;
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
    boundsRect = new Rect();
    rippleRect = new Rect();
  }

  public RippleView withColor(final int color) {
    paint.setColor(color);
    return this;
  }

  public RippleView hotSpot(final float centerX, final float centerY, final float maxRadius) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.maxRadius = maxRadius;
    this.radius = 0.0f;
    return this;
  }

  public RippleView withAlpha() {
    withAlpha = true;
    return this;
  }

  public RippleView withReverseAlpha() {
    withAlpha = true;
    reverseAlpha = true;
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

  public RippleView revert() {
    this.revert = true;
    return this;
  }

  public RippleView repeat(final int repeatMode, final int repeatCount) {
    this.repeatMode = repeatMode;
    this.repeatCount = repeatCount;
    return this;
  }

  public ValueAnimator ripple() {
    final ValueAnimator animator = ValueAnimator.ofFloat(revert ? 1.0f : 0.0f, revert ? 0.0f : 1.0f);
    animator.setDuration(duration);
    animator.setStartDelay(startDelay);
    animator.setInterpolator(revert ? new AccelerateInterpolator() : new DecelerateInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(final ValueAnimator animation) {
        animValue = (float) animation.getAnimatedValue();
        radius = maxRadius * animValue;
        if (withAlpha) {
          paint.setAlpha((int) (255 * (reverseAlpha ? 1.0f - animValue : animValue)));
        }
        rippleRect.set((int) (centerX - radius), (int) (centerY - radius), (int) (centerX + radius), (int) (centerY + radius));
        if (rippleRect.left < boundsRect.left) {
          rippleRect.left = boundsRect.left;
        }
        if (rippleRect.top < boundsRect.top) {
          rippleRect.top = boundsRect.top;
        }
        if (rippleRect.right > boundsRect.right) {
          rippleRect.right = boundsRect.right;
        }
        if (rippleRect.bottom > boundsRect.bottom) {
          rippleRect.bottom = boundsRect.bottom;
        }
        ViewCompat.postInvalidateOnAnimation(RippleView.this);
      }
    });

    animator.addListener(new AbstractAnimatorListener() {
      @Override public void onAnimationStart(final Animator animation) {
        currentLayerType = getLayerType();
        setLayerType(LAYER_TYPE_HARDWARE, null);
      }

      @Override public void onAnimationEnd(final Animator animation) {
        setLayerType(currentLayerType, null);
        if (endAction != null) {
          endAction.run();
          endAction = null;
        }
        revert = false;
        startDelay = 0l;
      }
    });

    animator.setRepeatMode(repeatMode);
    animator.setRepeatCount(repeatCount);

    return animator;
  }

  @Override protected void onDraw(final Canvas canvas) {
    if (radius > 0.0f) {
      canvas.clipRect(rippleRect);
      canvas.drawCircle(centerX, centerY, radius, paint);
    }
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    boundsRect.set(0, 0, w, h);
  }
}
