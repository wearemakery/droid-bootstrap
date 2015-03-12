package com.bootstrap.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TypedValue;

import java.util.List;

public final class AndroidUtils {
  private AndroidUtils() {
  }

  public static void safeCloseCursor(final Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }

  public static boolean gtIceCream() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static boolean gtJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  public static boolean gtKitKat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  public static boolean gtLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
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
    return (gtKitKat()) ? statusBarHeight(context) : 0;
  }

  public static boolean isNotificationAccessGranted(final Context context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
      return false;
    } else {
      final String enabledNotificationListeners = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
      return (!TextUtils.isEmpty(enabledNotificationListeners) && enabledNotificationListeners.contains(context.getPackageName()));
    }
  }

  public static int actionBarHeight(final Context context) {
    final TypedValue tv = new TypedValue();
    if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
    }
    return 0;
  }

  public static String getEnvironmentInfo(final Context context) {
    String info = Build.MANUFACTURER + " " + Build.MODEL + ", API level " + Build.VERSION.SDK_INT;
    try {
      final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      info += ", version " + packageInfo.versionName + "(" + packageInfo.versionCode + ")";
    } catch (final Exception e) {
      // ignore
    }
    return info;
  }
}
