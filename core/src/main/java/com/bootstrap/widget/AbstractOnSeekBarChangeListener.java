package com.bootstrap.widget;

import android.widget.SeekBar;

public abstract class AbstractOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
  @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
  }

  @Override public void onStartTrackingTouch(final SeekBar seekBar) {
  }

  @Override public void onStopTrackingTouch(final SeekBar seekBar) {
  }
}
