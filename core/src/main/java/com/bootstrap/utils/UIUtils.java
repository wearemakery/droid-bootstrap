package com.bootstrap.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class UIUtils {
  public static Drawable getRippleBackground(final int backgroundInactive, final int backgroundPressed) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      final ColorStateList backgroundColorStateList = new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed},}, new int[]{backgroundPressed});
      return new RippleDrawable(backgroundColorStateList, new ColorDrawable(backgroundInactive), new ShapeDrawable(new RectShape()));
    } else {
      final StateListDrawable backgroundDrawable = new StateListDrawable();
      backgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(backgroundPressed));
      backgroundDrawable.addState(new int[]{}, new ColorDrawable(backgroundInactive));
      return backgroundDrawable;
    }
  }

  public static ColorStateList getTextColor(final int textInactive, final int textPressed) {
    return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}}, new int[]{textPressed, textInactive});
  }

  public static int getActionBarHeight(final Context context){
    int actionBarHeight = 0;
    final TypedValue tv = new TypedValue();
    if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
    }
    return actionBarHeight;
  }
}
