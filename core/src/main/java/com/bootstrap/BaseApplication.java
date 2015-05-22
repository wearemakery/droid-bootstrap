package com.bootstrap;

import android.app.Application;
import android.content.Context;

import com.bootstrap.di.CoreComponent;
import com.bootstrap.di.CoreModule;
import com.bootstrap.di.DaggerCoreComponent;

public abstract class BaseApplication extends Application {
  protected CoreComponent coreComponent;

  public static BaseApplication from(final Context context) {
    return ((BaseApplication) context.getApplicationContext());
  }

  public CoreComponent getComponent() {
    return coreComponent;
  }

  @Override public void onCreate() {
    super.onCreate();
    coreComponent = DaggerCoreComponent.builder().coreModule(new CoreModule(this)).build();
  }
}