package com.bootstrap.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public final class ThreadAwareTree implements Timber.TaggedTree {
  private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
  private static final ThreadLocal<String> NEXT_TAG = new ThreadLocal<>();
  private static final String TAG_FORMAT = "[%s] %s";

  private static String createTag() {
    String tag = NEXT_TAG.get();
    if (tag != null) {
      NEXT_TAG.remove();
    } else {
      tag = new Throwable().getStackTrace()[4].getClassName();
      Matcher m = ANONYMOUS_CLASS.matcher(tag);
      if (m.find()) {
        tag = m.replaceAll("");
      }
    }
    return String.format(TAG_FORMAT, Thread.currentThread().getName(), tag.substring(tag.lastIndexOf('.') + 1));
  }

  static String formatString(String message, Object... args) {
    return args.length == 0 ? message : String.format(message, args);
  }

  @Override
  public void v(String message, Object... args) {
    Log.v(createTag(), formatString(message, args));
  }

  @Override
  public void v(Throwable t, String message, Object... args) {
    Log.v(createTag(), formatString(message, args), t);
  }

  @Override
  public void d(String message, Object... args) {
    Log.d(createTag(), formatString(message, args));
  }

  @Override
  public void d(Throwable t, String message, Object... args) {
    Log.d(createTag(), formatString(message, args), t);
  }

  @Override
  public void i(String message, Object... args) {
    Log.i(createTag(), formatString(message, args));
  }

  @Override
  public void i(Throwable t, String message, Object... args) {
    Log.i(createTag(), formatString(message, args), t);
  }

  @Override
  public void w(String message, Object... args) {
    Log.w(createTag(), formatString(message, args));
  }

  @Override
  public void w(Throwable t, String message, Object... args) {
    Log.w(createTag(), formatString(message, args), t);
  }

  @Override
  public void e(String message, Object... args) {
    Log.e(createTag(), formatString(message, args));
  }

  @Override
  public void e(Throwable t, String message, Object... args) {
    Log.e(createTag(), formatString(message, args), t);
  }

  @Override
  public void tag(String tag) {
    NEXT_TAG.set(tag);
  }
}
