package com.bootstrap.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;

import com.bootstrap.R;

public class ExpandableCardView extends CardView {
  private boolean expanded, animating;
  private int targetId;
  private View target;
  private RecyclerView recyclerView;
  private StateChangedListener stateChangedListener;

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

  public void expand(final boolean withAnimation) {
    if (animating || expanded) {
      return;
    }
    expanded = true;
    if (withAnimation) {
      animateExpanding();
    } else {
      final ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
      layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      target.setLayoutParams(layoutParams);
      target.setVisibility(View.VISIBLE);
    }
    if (stateChangedListener != null) {
      stateChangedListener.onStateChanged(this, true);
    }
  }

  public void collapse(final boolean withAnimation) {
    if (animating || !expanded) {
      return;
    }
    expanded = false;
    if (withAnimation) {
      animateCollapsing();
    } else {
      target.setVisibility(View.GONE);
    }
    if (stateChangedListener != null) {
      stateChangedListener.onStateChanged(this, false);
    }
  }

  public void setTargetId(final int targetId) {
    this.targetId = targetId;
  }

  public void setStateChangedListener(final StateChangedListener stateChangedListener) {
    this.stateChangedListener = stateChangedListener;
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
          collapse(true);
        } else {
          expand(true);
        }
      }
    });
  }

  private static ValueAnimator createHeightAnimator(final View view, final int start, final int end) {
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

  private void animateExpanding() {
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

    if (recyclerView != null) {
      final int recyclerViewHeight = recyclerView.getHeight();
      final int recyclerViewBottomPadding = recyclerView.getPaddingBottom();
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
        @Override public void onAnimationUpdate(final ValueAnimator animation) {
          if (recyclerView.getLayoutManager().canScrollVertically()) {
            final int bottom = getBottom();
            if (bottom > recyclerViewHeight) {
              final int top = getTop();
              if (top > 0) {
                recyclerView.smoothScrollBy(0, Math.min(bottom - recyclerViewHeight + recyclerViewBottomPadding + 4, top));
              }
            }
          }
        }
      });
    }

    animator.start();
  }

  private void animateCollapsing() {
    final int originalHeight = target.getHeight();
    final ValueAnimator animator = createHeightAnimator(target, originalHeight, 0);
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
    post(new Runnable() {
      @Override public void run() {
        ViewParent parent;
        do {
          parent = getParent();
          if (parent instanceof RecyclerView) {
            recyclerView = (RecyclerView) parent;
            break;
          }
        } while (parent == null);
      }
    });
  }

  public interface StateChangedListener {
    void onStateChanged(final View view, final boolean expanded);
  }
}
