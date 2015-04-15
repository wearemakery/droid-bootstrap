package com.bootstrap.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.R;

public class ExpandableCardView extends CardView {
  private boolean expanded, animating;
  private int targetId;
  private View target;

  public ExpandableCardView(final Context context) {
    super(context);
    init(context, null);
  }

  public ExpandableCardView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs);
  }

  public ExpandableCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  public void expand() {
    if (animating || expanded) {
      return;
    }
    expanded = true;
    animateExpanding();
  }

  public void collapse() {
    if (animating || !expanded) {
      return;
    }
    expanded = false;
    animateCollapsing();
  }

  private void init(final Context context, final AttributeSet attrs) {
    if (attrs != null) {
      final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ExpandableCardView);
      targetId = array.getResourceId(R.styleable.ExpandableCardView_target, 0);
      expanded = array.getBoolean(R.styleable.ExpandableCardView_expanded, false);
      array.recycle();
    }
    setOnClickListener(new OnClickListener() {
      @Override public void onClick(final View view) {
        if (expanded) {
          collapse();
        } else {
          expand();
        }
      }
    });
  }

  public static ValueAnimator createHeightAnimator(final View view, final int start, final int end) {
    final ValueAnimator animator = ValueAnimator.ofInt(start, end);
    animator.setInterpolator(new DecelerateInterpolator());
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(final ValueAnimator valueAnimator) {
        final int value = (Integer) valueAnimator.getAnimatedValue();
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = value;
        view.setLayoutParams(layoutParams);
      }
    });
    return animator;
  }

  public void animateExpanding() {
    target.setVisibility(View.VISIBLE);
    final View parent = (View) target.getParent();
    final int widthSpec = View.MeasureSpec.makeMeasureSpec(
      parent.getMeasuredWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
    final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    target.measure(widthSpec, heightSpec);

    final ValueAnimator animator = createHeightAnimator(target, 0, target.getMeasuredHeight());
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(final Animator animation) {
        animating = true;
      }

      @Override public void onAnimationEnd(final Animator animation) {
        animating = false;
      }
    });
    animator.start();
  }

  public void animateCollapsing() {
    final int origHeight = target.getHeight();

    final ValueAnimator animator = createHeightAnimator(target, origHeight, 0);
    animator.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationStart(final Animator animation) {
        animating = true;
      }

      @Override public void onAnimationEnd(final Animator animator) {
        target.setVisibility(View.GONE);
        animating = false;
      }
    });
    animator.start();
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    target = findViewById(targetId);
    target.setVisibility(expanded ? View.VISIBLE : View.GONE);
  }
}
