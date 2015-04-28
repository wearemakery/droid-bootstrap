package com.bootstrap;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.bootstrap.utils.AndroidUtils;

public abstract class BaseFragment extends Fragment {
  protected int actionBarHeight;
  protected int navBarCorrection;
  protected int statusBarCorrection;

  @Override public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final Activity activity = getActivity();
    actionBarHeight = AndroidUtils.actionBarHeight(activity);
    navBarCorrection = AndroidUtils.navBarCorrection(activity);
    statusBarCorrection = AndroidUtils.statusBarCorrection(activity);
  }
}
