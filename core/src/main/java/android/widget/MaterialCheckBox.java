package android.widget;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

import com.bootstrap.BaseApplication;
import com.bootstrap.R;
import com.bootstrap.drawable.TintedBitmapDrawable;

import javax.inject.Inject;

public class MaterialCheckBox extends CheckBox {

  public static final int MODE_RECTANGLULAR_OUTLINE = 0;
  public static final int MODE_RECTANGLULAR_FILLED = 1;
  public static final int MODE_ROUND_OUTLINE = 2;

  @Inject TypefaceManager typefaceManager;

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
      BaseApplication.from(context).inject(this);
      typefaceManager.setup(context, attrs, this);
      setButtonStyle(MODE_RECTANGLULAR_OUTLINE);
    }
  }

  public final void setButtonStyle(final int mode, final int tint) {
    final StateListDrawable stateListDrawable = new StateListDrawable();

    int resourceChecked, resourceWildCard;
    switch (mode) {
      case MODE_RECTANGLULAR_FILLED:
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
      stateListDrawable.addState(new int[]{android.R.attr.state_checked}, getResources().getDrawable(resourceChecked));
      stateListDrawable.addState(StateSet.WILD_CARD, getResources().getDrawable(resourceWildCard));
    } else {
      stateListDrawable.addState(new int[]{android.R.attr.state_checked}, new TintedBitmapDrawable(getResources(),resourceChecked, tint));
      stateListDrawable.addState(StateSet.WILD_CARD, new TintedBitmapDrawable(getResources(),resourceWildCard, tint));
    }
    setButtonDrawable(stateListDrawable);
  }

  public final void setButtonStyle(final int mode){
    setButtonStyle(mode, -1);
  }

}
