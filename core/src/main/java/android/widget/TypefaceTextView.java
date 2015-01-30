package android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import javax.inject.Inject;

import com.bootstrap.BaseApplication;

public final class TypefaceTextView extends TextView {
  @Inject TypefaceManager typefaceManager;

  public TypefaceTextView(final Context context) {
    super(context);
    init(context, null);
  }

  public TypefaceTextView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public TypefaceTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public TypefaceTextView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(final Context context, final AttributeSet attrs) {
    if (!isInEditMode()) {
      BaseApplication.from(context).inject(this);
      typefaceManager.setup(context, attrs, this);
    }
  }
}
