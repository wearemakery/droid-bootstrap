package com.bootstrap.utils;

import rx.Observer;
import timber.log.Timber;

public abstract class AbstractObserver<T> implements Observer<T> {
  public final static AbstractObserver<Object> INSTANCE = new AbstractObserver<Object>() {
  };

  @Override public void onCompleted() {
  }

  @Override public void onError(final Throwable e) {
    Timber.e(e, "");
  }

  @Override public void onNext(final T t) {
  }
}
