package android.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class TypefaceManager {
  private final Typeface fontRegular;
  private final Typeface fontMedium;

  @Inject public TypefaceManager(final Typeface fontRegular, @Font("medium") final Typeface fontMedium) {
    this.fontRegular = fontRegular;
    this.fontMedium = fontMedium;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN) public void setup(final Context context, final AttributeSet attrs, final TextView textView) {
    Typeface font = null;
    if (attrs != null) {
      final TypedArray styleValues = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.fontFamily});
      final String fontFamily = styleValues.getString(0);
      if (!TextUtils.isEmpty(fontFamily)) {
        switch (fontFamily) {
          case "medium":
            font = fontMedium;
            break;
        }
      }
      styleValues.recycle();
    }
    if (font == null) {
      font = fontRegular;
    }
    textView.setTypeface(font);
  }
}
