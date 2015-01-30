package com.bootstrap.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.bootstrap.R;

public final class FloatingButton extends View {
  private int iconX, iconY;
  private Bitmap shadow;
  private Bitmap button;
  private Bitmap icon;
  private Paint buttonPaint;
  private LightingColorFilter filter1;
  private LightingColorFilter filter2;
  private GestureDetector gestureDetector;
  private OnClickListener onClickListener;

  private final GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
    @Override public boolean onSingleTapConfirmed(final MotionEvent e) {
      if (onClickListener != null) {
        onClickListener.onClick(FloatingButton.this);
      }
      return true;
    }

    @Override public boolean onDown(final MotionEvent e) {
      return true;
    }
  };

  public FloatingButton(final Context context) {
    super(context);
    init();
  }

  public FloatingButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FloatingButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public void setup(final int resId, final int color1, final int color2) {
    filter1 = new LightingColorFilter(color1, 0);
    filter2 = new LightingColorFilter(color2, 0);
    icon = BitmapFactory.decodeResource(getResources(), resId);
    buttonPaint.setColorFilter(filter1);
    iconX = (button.getWidth() - icon.getWidth()) / 2;
    iconY = (button.getHeight() - icon.getHeight()) / 2;
    invalidate();
  }

  private void init() {
    buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    shadow = BitmapFactory.decodeResource(getResources(), R.drawable.float_shadow);
    button = BitmapFactory.decodeResource(getResources(), R.drawable.float_circle);
    gestureDetector = new GestureDetector(getContext(), gestureListener);
  }

  public int getSize() {
    return button.getWidth();
  }

  @Override public void setOnClickListener(final OnClickListener listener) {
    this.onClickListener = listener;
  }

  @Override protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    final int desiredHSpec = MeasureSpec.makeMeasureSpec(button.getHeight(), MeasureSpec.EXACTLY);
    final int desiredWSpec = MeasureSpec.makeMeasureSpec(button.getWidth(), MeasureSpec.EXACTLY);
    setMeasuredDimension(desiredWSpec, desiredHSpec);
  }

  @Override protected void onDraw(final Canvas canvas) {
    canvas.drawBitmap(shadow, 0, 0, null);
    canvas.drawBitmap(button, 0, 0, buttonPaint);
    if (icon != null) {
      canvas.drawBitmap(icon, iconX, iconY, null);
    }
  }

  @Override public boolean onTouchEvent(final MotionEvent event) {
    final int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        buttonPaint.setColorFilter(filter2);
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        buttonPaint.setColorFilter(filter1);
        invalidate();
        break;
    }
    return gestureDetector.onTouchEvent(event);
  }
}
