package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.bootstrap.BaseApplication;

import javax.inject.Inject;

public final class FlatButton extends TextView {

  @Inject TypefaceManager typefaceManager;

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
      BaseApplication.from(getContext()).inject(this);
      setTypeface(typefaceManager.getMedium());
      setTextSize(TypedValue.COMPLEX_UNIT_SP, FONT_SIZE);
      setAllCaps(true);
    }
  }

  private void addRippleEffect(final int inactive, final int pressed) {
    final ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed},}, new int[]{pressed});
    final RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, new ColorDrawable(inactive), new ShapeDrawable(new RectShape()));
    setBackground(rippleDrawable);
  }

  public void setColors(final int textInactive, final int textPressed, final int backgroundInactive, final int backgroundPressed) {
    final ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}},
      new int[]{textPressed, textInactive});
    setTextColor(colorStateList);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      addRippleEffect(backgroundInactive, backgroundPressed);
    } else {
      final StateListDrawable backgroundDrawable = new StateListDrawable();
      backgroundDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(backgroundPressed));
      backgroundDrawable.addState(new int[]{}, new ColorDrawable(backgroundInactive));
      setBackground(backgroundDrawable);
    }
  }
}