package com.bootstrap.text;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

public final class CustomTypefaceSpan extends MetricAffectingSpan {
  private final Typeface typeface;

  public CustomTypefaceSpan(final Typeface typeface) {
    this.typeface = typeface;
  }

  @Override public void updateDrawState(final TextPaint drawState) {
    apply(drawState);
  }

  @Override public void updateMeasureState(final TextPaint paint) {
    apply(paint);
  }

  private void apply(final Paint paint) {
    paint.setTypeface(typeface);
  }
}
