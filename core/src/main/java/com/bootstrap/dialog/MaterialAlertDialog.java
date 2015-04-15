package com.bootstrap.dialog;


import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bootstrap.BaseApplication;
import com.bootstrap.R;
import com.bootstrap.event.AlertConfirmedEvent;
import com.bootstrap.view.TouchStealListener;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public final class MaterialAlertDialog extends DialogFragment {
  private final static String EXTRA_TITLE = "Title";
  private final static String EXTRA_MESSAGE = "Message";

  @Inject DisplayMetrics displayMetrics;
  @Inject EventBus eventBus;

  private String title;
  private String message;

  @Override public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseApplication.from(getActivity()).inject(this);

    setStyle(DialogFragment.STYLE_NO_FRAME, 0);

    final Bundle extras = getArguments();
    title = extras.getString(EXTRA_TITLE);
    message = extras.getString(EXTRA_MESSAGE);
  }

  @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.dialog_alert, container, false);
    final FrameLayout layoutBg = (FrameLayout) root.findViewById(R.id.fl_alert_bg);
    final CardView cardView = (CardView) root.findViewById(R.id.cv_alert);
    final TextView txtTitle = (TextView) root.findViewById(R.id.tv_alert_title);
    final TextView txtMessage = (TextView) root.findViewById(R.id.tv_alert_message);
    final Button btnOk = (Button) root.findViewById(R.id.b_alert_ok);

    final ViewGroup.LayoutParams lpBg = layoutBg.getLayoutParams();
    lpBg.width = displayMetrics.widthPixels;
    lpBg.height = displayMetrics.heightPixels;

    final ViewGroup.LayoutParams lpCard = cardView.getLayoutParams();
    lpCard.width = (int) (displayMetrics.widthPixels * 0.8f);

    txtTitle.setTextColor(Color.BLACK);
    txtTitle.setText(title);

    txtMessage.setTextColor(Color.BLACK);
    txtMessage.setText(message);

    cardView.setOnTouchListener(TouchStealListener.INSTANCE);

    btnOk.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View view) {
        dismissAllowingStateLoss();
        eventBus.post(new AlertConfirmedEvent());
      }
    });

    return root;
  }

  public static MaterialAlertDialog from(final String title, final String message) {
    final MaterialAlertDialog dialog = new MaterialAlertDialog();
    final Bundle extras = new Bundle();
    extras.putString(EXTRA_TITLE, title);
    extras.putString(EXTRA_MESSAGE, message);
    dialog.setArguments(extras);
    return dialog;
  }
}
