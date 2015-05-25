package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.bootstrap.R;
import com.bootstrap.drawable.TintedNinePatchDrawable;

public class MaterialEditText extends TypefaceEditText {
  public MaterialEditText(final Context context) {
    super(context);
    init(context, null);
  }

  public MaterialEditText(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public MaterialEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public MaterialEditText(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  @SuppressWarnings("deprecation") private void init(final Context context, final AttributeSet attrs) {
    final Resources resources = getResources();
    final Drawable normal = new TintedNinePatchDrawable(resources, R.drawable.input_line, 0xddffffff);
    final Drawable focused = new TintedNinePatchDrawable(resources, R.drawable.input_line_focused, Color.WHITE);
    final StateListDrawable bgDrawable = new StateListDrawable();
    bgDrawable.addState(new int[]{android.R.attr.state_focused}, focused);
    bgDrawable.addState(new int[]{android.R.attr.state_enabled}, normal);
    setBackgroundDrawable(bgDrawable);
    setTextColor(Color.WHITE);
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
  }
}
