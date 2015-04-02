package com.bootstrap.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public final class ThreadAwareTree extends Timber.DebugTree {
  private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
  private static final String TAG_FORMAT = "[%s] %s";

  @Override protected String createTag() {
    String tag = nextTag();
    if (tag != null) {
      return tag;
    }
    final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
    if (stackTrace.length < 6) {
      throw new IllegalStateException(
        "Synthetic stacktrace didn't have enough elements: are you using proguard?");
    }
    tag = stackTrace[5].getClassName();
    final Matcher matcher = ANONYMOUS_CLASS.matcher(tag);
    if (matcher.find()) {
      tag = matcher.replaceAll("");
    }
    tag = tag.substring(tag.lastIndexOf('.') + 1);
    return String.format(TAG_FORMAT, Thread.currentThread().getName(), tag);
  }
}
