package android.widget;

import android.content.Context;
import android.util.AttributeSet;

import javax.inject.Inject;

import com.bootstrap.BaseApplication;

public final class TypefaceEditText extends EditText {
  @Inject TypefaceManager typefaceManager;

  public TypefaceEditText(final Context context) {
    super(context);
    init(context, null);
  }

  public TypefaceEditText(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public TypefaceEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public TypefaceEditText(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
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
