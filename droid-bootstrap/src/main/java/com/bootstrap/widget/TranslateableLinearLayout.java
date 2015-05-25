package com.bootstrap.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class TranslateableLinearLayout extends LinearLayout {

  private int width = -1;

  public TranslateableLinearLayout(final Context context) {
    super(context);
  }

  public TranslateableLinearLayout(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public TranslateableLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public TranslateableLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public float getXFraction() {
    if (getWidth() == 0) return 0f;
    if (width == -1) width = getWidth();
    return getX() / width;
  }

  public void setXFraction(float xFraction) {
    if (width == -1 && getWidth() != 0) width = getWidth();
    setX((width > 0) ? (xFraction * width) : -9999);
  }
}
