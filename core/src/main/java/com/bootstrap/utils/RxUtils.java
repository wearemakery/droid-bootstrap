package com.bootstrap.utils;

import rx.Subscription;

public final class RxUtils {
  private RxUtils() {
  }

  public static void safeUnsubscribe(final Subscription subscription) {
    if (subscription != null) {
      subscription.unsubscribe();
    }
  }
}
