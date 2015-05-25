package com.bootstrap.view;

import android.view.MotionEvent;
import android.view.View;

public final class TouchStealListener implements View.OnTouchListener {
  private TouchStealListener() {
  }

  public final static TouchStealListener INSTANCE = new TouchStealListener();

  @Override public boolean onTouch(final View view, final MotionEvent event) {
    return true;
  }
}
