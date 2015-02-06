package com.bootstrap.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;

public final class AndroidUtils {
  private AndroidUtils() {
  }

  public static void safeCloseCursor(final Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }

  public static boolean gtIceCream() {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static boolean gtJellyBean() {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN;
  }

  private static int statusBarHeight(final Context context) {
    int result = 0;
    final Resources resources = context.getResources();
    final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = resources.getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static int statusBarCorrection(final Context context) {
    return (gtJellyBean()) ? statusBarHeight(context) : 0;
  }
}
