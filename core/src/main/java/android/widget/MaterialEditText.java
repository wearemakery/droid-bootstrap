package android.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.bootstrap.R;

public class MaterialEditText extends LinearLayout {
  private EditText editText;

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
    editText = new TypefaceEditText(context);
    editText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.input_line_bg));
    editText.setTextColor(Color.WHITE);
    addView(editText);
  }
}
