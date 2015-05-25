package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.util.TypedValue;

import com.bootstrap.BaseApplication;
import com.bootstrap.R;
import com.bootstrap.drawable.TintedBitmapDrawable;
import com.bootstrap.utils.AndroidUtils;

import javax.inject.Inject;

public class MaterialCheckBox extends CheckBox {
  public static final int MODE_RECTANGULAR_OUTLINE = 0;
  public static final int MODE_RECTANGULAR_FILLED = 1;
  public static final int MODE_ROUND_OUTLINE = 2;

  @Inject TypefaceManager typefaceManager;
  @Inject Resources resources;
  @Inject DisplayMetrics displayMetrics;

  public MaterialCheckBox(final Context context) {
    super(context);
    init(context, null);
  }

  public MaterialCheckBox(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public MaterialCheckBox(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public MaterialCheckBox(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(final Context context, final AttributeSet attrs) {
    if (!isInEditMode()) {
      BaseApplication.from(context).getComponent().inject(this);
      typefaceManager.setup(context, attrs, this);
      int paddingLeft = getPaddingLeft();
      if (paddingLeft == 0) {
        paddingLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, displayMetrics);
      }
      if (AndroidUtils.ltJellyBean()) {
        paddingLeft += (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32f, displayMetrics);
      }
      setPadding(paddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
    setButtonStyle(MODE_RECTANGULAR_OUTLINE);
  }

  @SuppressWarnings("deprecation") public void setButtonStyle(final int mode, final int tint) {
    final StateListDrawable stateListDrawable = new StateListDrawable();

    int resourceChecked, resourceWildCard;
    switch (mode) {
      case MODE_RECTANGULAR_FILLED:
        resourceChecked = R.drawable.ic_check_box_filled_on;
        resourceWildCard = R.drawable.ic_check_box_filled_off;
        break;
      case MODE_ROUND_OUTLINE:
        resourceChecked = R.drawable.ic_check_round_filled_on;
        resourceWildCard = R.drawable.ic_check_round_filled_off;
        break;
      default:
        resourceChecked = R.drawable.ic_check_box_outline_on;
        resourceWildCard = R.drawable.ic_check_box_outline_off;
        break;
    }
    if (tint == -1) {
      stateListDrawable.addState(new int[]{android.R.attr.state_checked}, resources.getDrawable(resourceChecked));
      stateListDrawable.addState(StateSet.WILD_CARD, resources.getDrawable(resourceWildCard));
    } else {
      stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new TintedBitmapDrawable(resources, resourceChecked, tint));
      stateListDrawable.addState(StateSet.WILD_CARD, new TintedBitmapDrawable(resources, resourceWildCard, tint));
    }
    setButtonDrawable(stateListDrawable);
  }

  public void setButtonStyle(final int mode) {
    setButtonStyle(mode, -1);
  }
}
