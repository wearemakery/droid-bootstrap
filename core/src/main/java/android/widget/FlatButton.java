package android.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.bootstrap.BaseApplication;
import com.bootstrap.utils.UIUtils;

import javax.inject.Inject;

public final class FlatButton extends TextView {
  @Inject TypefaceManager typefaceManager;
  @Inject Handler handler;

  private static final int FONT_SIZE = 14;

  public FlatButton(Context context) {
    super(context);
    init();
  }

  public FlatButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public FlatButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public FlatButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    if (!isInEditMode()) {
      BaseApplication.from(getContext()).getComponent().inject(this);
      setTypeface(typefaceManager.getMedium());
      setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
      setAllCaps(true);
      setGravity(Gravity.CENTER);
    }
  }

  @SuppressWarnings("deprecation")
  public void setColors(final int textInactive, final int textPressed, final int backgroundInactive, final int backgroundPressed) {
    setTextColor(UIUtils.getTextColor(textInactive, textPressed));
    setBackgroundDrawable(UIUtils.getRippleBackground(backgroundInactive, backgroundPressed));
  }
}
