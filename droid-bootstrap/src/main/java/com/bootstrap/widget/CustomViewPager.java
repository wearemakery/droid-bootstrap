package com.bootstrap.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public final class CustomViewPager extends ViewPager {
  private boolean enabled;

  public CustomViewPager(final Context context) {
    super(context);
    init();
  }

  public CustomViewPager(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setEnabled(true);
  }

  @Override public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return enabled && super.onTouchEvent(event);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent event) {
    return enabled && super.onInterceptTouchEvent(event);
  }
}
