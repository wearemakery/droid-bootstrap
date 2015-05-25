package com.bootstrap.utils;

import timber.log.Timber;

public final class ThreadAwareTree extends Timber.DebugTree {
  private static final String TAG_FORMAT = "[%s] %s";

  @Override protected String createStackElementTag(final StackTraceElement element) {
    String tag = super.createStackElementTag(element);
    return String.format(TAG_FORMAT, Thread.currentThread().getName(), tag);
  }
}
