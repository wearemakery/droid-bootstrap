package com.bootstrap.analytics;

import android.content.Context;

import com.bootstrap.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class AnalyticsService {
  private final Context context;

  private Tracker tracker;

  @Inject public AnalyticsService(final Context context) {
    this.context = context;
  }

  public void init(final boolean isDebug) {
    final GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
    analytics.setLocalDispatchPeriod(300);
    analytics.getLogger().setLogLevel(isDebug ? Logger.LogLevel.VERBOSE : Logger.LogLevel.ERROR);
    tracker = analytics.newTracker(R.xml.ga_config);
    tracker.enableAdvertisingIdCollection(true);
  }

  public ScreenTracker screen(final String screenName) {
    return ScreenTracker.from(tracker, screenName);
  }

  public EventTracker event() {
    return EventTracker.from(tracker);
  }

  public TimingTracker timing() {
    return TimingTracker.from(tracker);
  }
}
