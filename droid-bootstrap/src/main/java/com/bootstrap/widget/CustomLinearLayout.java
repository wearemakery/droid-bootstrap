package com.bootstrap.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout {
  private boolean enabled;

  public CustomLinearLayout(final Context context) {
    super(context);
    init();
  }

  public CustomLinearLayout(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public CustomLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    setEnabled(true);
  }

  @Override public void setEnabled(final boolean enabled) {
    super.setEnabled(enabled);
    this.enabled = enabled;
  }

  @Override public boolean onTouchEvent(final MotionEvent event) {
    return enabled && super.onTouchEvent(event);
  }

  @Override public boolean onInterceptTouchEvent(final MotionEvent event) {
    return !enabled || super.onInterceptTouchEvent(event);
  }
}
