package com.bootstrap.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.animation.AbstractAnimatorListener;
import com.bootstrap.utils.ColorUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public final class MaterialImageView extends View implements Target {
  private int currentLayerType;
  private RectF rect;
  private Paint paint;
  private Bitmap bitmap;
  private Runnable loadAction;
  private ValueAnimator animator;

  public MaterialImageView(final Context context) {
    super(context);
    init();
  }

  public MaterialImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MaterialImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public MaterialImageView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public MaterialImageView setLoadAction(final Runnable runnable) {
    loadAction = runnable;
    return this;
  }

  private void init() {
    rect = new RectF();
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  }

  public void setBitmap(final Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  @Override protected void onDraw(final Canvas canvas) {
    if (getBackground() != null) {
      getBackground().draw(canvas);
    }
    if (bitmap != null) {
      canvas.drawBitmap(bitmap, rect.left, rect.top, paint);
    }
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    rect.set(0f, 0f, w, h);
    if (bitmap != null) {
      rect.inset((rect.width() - bitmap.getWidth()) / 2, (rect.height() - bitmap.getHeight()) / 2);
    }
  }

  @Override public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
    if (loadAction != null) {
      loadAction.run();
      loadAction = null;
    }

    this.bitmap = bitmap;

    final ColorMatrix contrastMatrix = new ColorMatrix();
    final ColorMatrix saturationMatrix = new ColorMatrix();
    final ColorMatrix resultMatrix = new ColorMatrix();

    animator = ValueAnimator.ofFloat(0f, 4f);
    animator.setInterpolator(new DecelerateInterpolator(1.5f));
    animator.setDuration(3000l);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(final ValueAnimator animation) {
        final float value = (float) animation.getAnimatedValue();
        final float alpha = Math.min(value, 2f) / 2f; // dt = 2, v = [0.0, 1.0]
        final float contrast = Math.min(value, 3f) / 3f - 1f; // dt = 3, v = [-1.0, 0.0]
        final float saturation = value / 4f - 1f; // dt = 4, v = [-1.0, 0.0]
        ColorUtils.alpha(alpha, contrastMatrix);
        ColorUtils.contrast(contrast, contrastMatrix);
        ColorUtils.saturation(saturation, saturationMatrix);
        resultMatrix.setConcat(contrastMatrix, saturationMatrix);
        paint.setColorFilter(new ColorMatrixColorFilter(resultMatrix));
        ViewCompat.postInvalidateOnAnimation(MaterialImageView.this);
      }
    });
    animator.addListener(new AbstractAnimatorListener() {
      @Override public void onAnimationStart(final Animator animation) {
        currentLayerType = getLayerType();
        setLayerType(LAYER_TYPE_HARDWARE, null);
      }

      @Override public void onAnimationEnd(final Animator animation) {
        setLayerType(currentLayerType, null);
      }
    });
    animator.start();
  }

  @Override public void onBitmapFailed(final Drawable errorDrawable) {
  }

  @Override public void onPrepareLoad(final Drawable placeHolderDrawable) {
  }

  @Override protected void onDetachedFromWindow() {
    if (animator != null && animator.isRunning()) {
      animator.cancel();
    }
    super.onDetachedFromWindow();
  }
}
