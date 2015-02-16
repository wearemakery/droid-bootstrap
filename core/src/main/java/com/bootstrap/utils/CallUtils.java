package com.bootstrap.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.provider.CallLog;
import android.view.View;
import android.view.WindowManager;

import java.util.Iterator;

public final class CallUtils {

  public static boolean dismissMissedCalls(final Activity activity) {
    Cursor cursor = null;
    try {
      cursor = activity.getContentResolver().query(CallLog.Calls.CONTENT_URI,
        new String[]{CallLog.Calls._ID},
        CallLog.Calls.TYPE + "=" + CallLog.Calls.MISSED_TYPE + " AND " + CallLog.Calls.NEW + "=1",
        null,
        null);

      if (cursor.getCount() > 0) {
        final Intent showCallLog = new Intent()
          .setAction(Intent.ACTION_VIEW)
          .setType(android.provider.CallLog.Calls.CONTENT_TYPE)
          .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        final Iterator localIterator = activity.getPackageManager().queryIntentActivities(showCallLog, 65536).iterator();
        ResolveInfo localResolveInfo = null;
        String packageName = null;
        do {
          if (!localIterator.hasNext()) {
            break;
          }
          localResolveInfo = (ResolveInfo) localIterator.next();
          packageName = localResolveInfo.activityInfo.packageName;
        }
        while (localResolveInfo.activityInfo.applicationInfo.flags == 0);
        if (localResolveInfo != null) {
          showCallLog.setComponent(new ComponentName(packageName, localResolveInfo.activityInfo.name));
        }
        activity.startActivityForResult(showCallLog, 255);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
          WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
          WindowManager.LayoutParams.TYPE_PHONE,
          WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
            | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
          PixelFormat.TRANSPARENT);

        final View overlayView = new View(activity);
        overlayView.setBackgroundColor(Color.RED);
        final WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlayView, params);

        overlayView.animate().alpha(0f).setDuration(1700).setStartDelay(300).start();

        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            activity.finishActivity(255);
          }
        }, 200);
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            windowManager.removeView(overlayView);
          }
        }, 2000);
        return true;
      }
    } catch (Exception e) {
      // Couldn't show call log.
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return false;
  }
  
}
