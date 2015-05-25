package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.bootstrap.BaseApplication;
import com.bootstrap.R;

import javax.inject.Inject;

public class MaterialButton extends Button {
  @Inject TypefaceManager typefaceManager;
  @Inject DisplayMetrics displayMetrics;
  @Inject Resources resources;

  private boolean pressed;
  private int height;
  private int touchTarget;
  private int externalPadding;
  private float radius;
  private RectF bgRect;
  private Paint bgPaint;

  public MaterialButton(final Context context) {
    super(context);
    init(context, null);
  }

  public MaterialButton(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public MaterialButton(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public MaterialButton(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context, attrs);
  }

  private void init(final Context context, final AttributeSet attrs) {
    bgRect = new RectF();
    bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    bgPaint.setColor(0xffcccccc);
    if (!isInEditMode()) {
      BaseApplication.from(context).getComponent().inject(this);
      setTypeface(typefaceManager.getMedium());

      final int minWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64.0f, displayMetrics);
      final int internalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8.0f, displayMetrics);

      height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36.0f, displayMetrics);
      touchTarget = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48.0f, displayMetrics);
      externalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4.0f, displayMetrics);
      radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3.0f, displayMetrics);

      setMinWidth(minWidth);
      setPadding(internalPadding, 0, internalPadding, 0);
      setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.abc_text_size_button_material));
    }
    if (attrs != null) {
      final TypedArray styleValues = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.textColor});
      setTextColor(styleValues.getColor(0, Color.BLACK));
      styleValues.recycle();
    } else {
      setTextColor(Color.BLACK);
    }
    setAllCaps(true);
    setBackgroundColor(Color.TRANSPARENT);

    ViewGroup.LayoutParams lp = getLayoutParams();
    if (lp == null) {
      lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      setLayoutParams(lp);
    }
  }

  @Override protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
    final int height = MeasureSpec.makeMeasureSpec(touchTarget, MeasureSpec.EXACTLY);
    super.onMeasure(widthMeasureSpec, height);
  }

  @Override protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    bgRect.set(0, 0, w, h);
    bgRect.inset(externalPadding, (touchTarget - height) / 2);
  }

  @Override public boolean onTouchEvent(final MotionEvent event) {
    final int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        pressed = true;
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_CANCEL:
        pressed = false;
        invalidate();
        break;
    }
    return super.onTouchEvent(event);
  }

  @Override protected void onDraw(final Canvas canvas) {
    if (pressed) {
      canvas.drawRoundRect(bgRect, radius, radius, bgPaint);
    }
    super.onDraw(canvas);
  }
}
