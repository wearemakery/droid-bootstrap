package com.bootstrap.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.Font;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class CoreModule {
  private final Application application;

  public CoreModule(final Application application) {
    this.application = application;
  }

  @Provides @Singleton public Context provideContext() {
    return application;
  }

  @Provides @Singleton public Resources provideResources(final Context context) {
    return context.getResources();
  }

  @Provides @Singleton public DisplayMetrics provideDisplayMetrics(final Context context) {
    return context.getResources().getDisplayMetrics();
  }

  @Provides @Singleton public NotificationManagerCompat provideNotificationManager(final Context context) {
    return NotificationManagerCompat.from(context);
  }

  @Provides @Singleton public SharedPreferences provideSharedPreferences(final Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  @Provides @Singleton public Handler provideHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @Singleton public EventBus provideEventBus() {
    return EventBus.getDefault();
  }

  @Provides @Singleton public Typeface provideRegularFont() {
    return Typeface.createFromAsset(application.getAssets(), "Roboto-Regular.ttf");
  }

  @Provides @Singleton @Font("medium") public Typeface provideMediumFont() {
    return Typeface.createFromAsset(application.getAssets(), "Roboto-Medium.ttf");
  }

  @Provides @Singleton public InputMethodManager provideInputMethodManager() {
    return (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
  }
}
