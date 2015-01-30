package com.bootstrap;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Font;
import android.widget.TypefaceTextView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true,
  injects = {
    TypefaceTextView.class
  }
)
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

  @Provides @Singleton public LayoutInflater provideInfalter(final Context context) {
    return LayoutInflater.from(context);
  }

  @Provides @Singleton public WindowManager provideWindowManager(final Context context) {
    return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }

  @Provides @Singleton public Handler provideHandler() {
    return new Handler();
  }

  @Provides @Singleton public Typeface provideRegularFont() {
    return Typeface.createFromAsset(application.getAssets(), "Roboto-Regular.ttf");
  }

  @Provides @Singleton @Font("medium") public Typeface provideMediumFont() {
    return Typeface.createFromAsset(application.getAssets(), "Roboto-Medium.ttf");
  }
}
