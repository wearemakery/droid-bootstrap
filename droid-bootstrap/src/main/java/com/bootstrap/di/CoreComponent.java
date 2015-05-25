package com.bootstrap.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.FlatButton;
import android.widget.Font;
import android.widget.MaterialButton;
import android.widget.MaterialCheckBox;
import android.widget.MaterialEditText;
import android.widget.TypefaceEditText;
import android.widget.TypefaceManager;
import android.widget.TypefaceTextView;

import com.bootstrap.analytics.AnalyticsService;
import com.bootstrap.dialog.MaterialAlertDialog;

import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;

@Singleton
@Component(modules = {
  CoreModule.class
})
public interface CoreComponent {

  void inject(FlatButton flatButton);

  void inject(MaterialButton materialButton);

  void inject(MaterialCheckBox materialCheckBox);

  void inject(TypefaceEditText typefaceEditText);

  void inject(TypefaceTextView typefaceTextView);

  void inject(MaterialAlertDialog materialAlertDialog);

  void inject(MaterialEditText materialEditText);

  Context context();

  Resources resources();

  DisplayMetrics displayMetrics();

  NotificationManagerCompat notificationManagerCompat();

  SharedPreferences sharedPreferences();

  Handler handler();

  EventBus eventBus();

  Typeface typeface();

  @Font("medium") Typeface typefaceMedium();

  TypefaceManager typefaceManager();

  InputMethodManager inputMethodManager();

  AnalyticsService analyticsService();
}
