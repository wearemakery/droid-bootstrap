package com.bootstrap.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;

public final class UIUtils {
  public static Drawable getRippleBackground(final int backgroundColor, final int rippleColor) {
    return getRippleBackground(backgroundColor, rippleColor, true);
  }

  public static Drawable getRippleBackground(final int backgroundColor, final int rippleColor, final boolean clip) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      final ColorStateList rippleStateList = new ColorStateList(
        new int[][]{
          new int[]{android.R.attr.state_pressed},
          new int[0]
        },
        new int[]{
          rippleColor,
          rippleColor
        });
      final int alpha = Color.alpha(backgroundColor);
      final Drawable content = alpha > 0 ? new ColorDrawable(backgroundColor) : null;
      final Drawable mask = clip ? new ShapeDrawable(new RectShape()) : null;
      return new RippleDrawable(rippleStateList, content, mask);
    } else {
      final StateListDrawable backgroundDrawable = new StateListDrawable();
      backgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(rippleColor));
      backgroundDrawable.addState(new int[]{}, new ColorDrawable(backgroundColor));
      return backgroundDrawable;
    }
  }

  public static ColorStateList getTextColor(final int textInactive, final int textPressed) {
    return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}}, new int[]{textPressed, textInactive});
  }
}
