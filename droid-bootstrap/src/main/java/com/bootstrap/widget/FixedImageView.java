package com.bootstrap.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FixedImageView extends ImageView {
  private int fixedSize;

  public FixedImageView(final Context context) {
    super(context);
  }

  public FixedImageView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public FixedImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public FixedImageView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setFixedSize(final int fixedSize) {
    this.fixedSize = fixedSize;
  }

  @Override protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    final int width = MeasureSpec.makeMeasureSpec(fixedSize, MeasureSpec.EXACTLY);
    final int height = MeasureSpec.makeMeasureSpec(fixedSize, MeasureSpec.EXACTLY);
    super.onMeasure(width, height);
  }
}
