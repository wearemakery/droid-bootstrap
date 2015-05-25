package com.bootstrap.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class TargetLinearLayout extends LinearLayout implements Target {
  public TargetLinearLayout(final Context context) {
    super(context);
  }

  public TargetLinearLayout(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public TargetLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public TargetLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @SuppressWarnings("deprecation") @Override public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
    setBackgroundDrawable(new BitmapDrawable(bitmap));
  }

  @Override public void onBitmapFailed(final Drawable errorDrawable) {
  }

  @Override public void onPrepareLoad(final Drawable placeHolderDrawable) {
  }
}
