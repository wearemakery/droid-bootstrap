package com.bootstrap.utils;

import rx.Subscription;

public final class RxUtils {
  private RxUtils() {
  }

  public static void safeUnsubscribe(final Subscription... subscriptions) {
    for (Subscription subscription : subscriptions) {
      if (subscription != null) {
        subscription.unsubscribe();
      }
    }
  }
}
