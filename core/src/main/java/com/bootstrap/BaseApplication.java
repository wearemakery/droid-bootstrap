package com.bootstrap;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;

public abstract class BaseApplication extends Application {
  protected ObjectGraph objectGraph;

  public static BaseApplication from(final Context context) {
    return ((BaseApplication) context.getApplicationContext());
  }

  public void inject(final Object target) {
    objectGraph.inject(target);
  }

  @Override public void onCreate() {
    super.onCreate();
    objectGraph = ObjectGraph.create(new CoreModule(this)).plus(getModule());
  }

  public abstract Object getModule();
}
