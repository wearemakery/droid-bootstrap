package android.widget;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;

import com.bootstrap.BaseApplication;
import com.bootstrap.R;

import javax.inject.Inject;

public class MaterialCheckBox extends CheckBox {

  public static final int MODE_RECTANGLULAR = 0;
  public static final int MODE_ROUND = 1;

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
      setButtonStyle(MODE_RECTANGLULAR);
    }
  }

  public final void setButtonStyle(final int mode) {
    final StateListDrawable stateListDrawable = new StateListDrawable();
    stateListDrawable.addState(new int[]{android.R.attr.state_checked}, getResources().getDrawable(mode == MODE_RECTANGLULAR ? R.drawable.ic_check_box_grey600_24dp : R.drawable.ic_check_round_grey600_24dp));
    stateListDrawable.addState(StateSet.WILD_CARD, getResources().getDrawable(mode == MODE_RECTANGLULAR ? R.drawable.ic_check_box_outline_blank_grey600_24dp : R.drawable.ic_check_round_outline_blank_grey600_24dp));
    setButtonDrawable(stateListDrawable);
  }

}
