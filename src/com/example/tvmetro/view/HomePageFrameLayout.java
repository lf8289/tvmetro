package com.example.tvmetro.view;

import com.example.tvmetro.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class HomePageFrameLayout extends FrameLayout implements
        View.OnFocusChangeListener {

    private Context mContext;
    private ImageView mShaderView;

    private AnimatorSet mCurAnimatorSet;

    private int mBorderWidth;
    private int mBorderHeight;

    private Rect mShaderRect = new Rect();
    private View mCurFocusView;

    public HomePageFrameLayout(Context context) {
        this(context, null, 0);
    }

    public HomePageFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        mShaderView = new ImageView(mContext);
        mShaderView.setBackgroundResource(R.drawable.white_border);
        addView(mShaderView);
        mShaderView.setVisibility(View.INVISIBLE);

        Drawable bd = context.getResources().getDrawable(
                R.drawable.white_border);
        bd.getPadding(mShaderRect);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            mShaderView.setVisibility(View.INVISIBLE);
        } else {
            mShaderView.setVisibility(View.VISIBLE);

            if (mCurAnimatorSet != null) {
                mCurAnimatorSet.cancel();
            }

            Rect r1 = new Rect();
            view.getDrawingRect(r1);
            offsetDescendantRectToMyCoords(view, r1);

            int reflectHeight = 0;
            HomePageCellView smi = (HomePageCellView) view;
            MetroItemView itemView = smi.getMetroItemView();
            if (itemView.isReflection()) {
                reflectHeight = (int) Math
                        .round(smi.getReflectHeight() * 1.105);
            }

            int i = r1.left - mShaderRect.left + view.getPaddingLeft();
            int j = r1.top - mShaderRect.top + view.getPaddingTop();
            int k = r1.right + mShaderRect.right - view.getPaddingRight();
            int m = r1.bottom + mShaderRect.bottom - view.getPaddingBottom();

            int i1 = (int) (0.105 * (k - i) / 2);
            int i2 = (int) (0.105 * (m - j) / 2);
            int i3 = i - i1;
            int i4 = j - i2;
            int i5 = i1 + k;
            int i6 = m + i2 - reflectHeight;

            AnimatorSet set = new AnimatorSet();
            mCurAnimatorSet = set;

            set.playTogether(ObjectAnimator.ofFloat(mShaderView, "x", i3));
            set.playTogether(ObjectAnimator.ofFloat(mShaderView, "y", i4));

            ViewGroup.LayoutParams params = mShaderView.getLayoutParams();
            if (params.width != (i5 - i3)) {
                ValueAnimator wa = ValueAnimator.ofInt(params.width, i5 - i3);
                wa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mBorderWidth = (Integer) animation.getAnimatedValue();
                        relayoutShaderBorder();
                    }
                });
                set.playTogether(wa);
            }
            if (params.height != i6 - i4) {
                ValueAnimator wh = ValueAnimator.ofInt(params.height, i6 - i4);
                wh.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mBorderHeight = (Integer) animation.getAnimatedValue();
                        relayoutShaderBorder();
                    }
                });
                set.playTogether(wh);
            }

            int duration = mCurFocusView == null ? 0 : 250;
            set.setDuration(duration);
            set.addListener(mShaderAnimListener);
            set.start();
            mShaderView.invalidate();
            mCurFocusView = view;
        }
    }

    private void relayoutShaderBorder() {
        ViewGroup.LayoutParams params = mShaderView.getLayoutParams();
        params.width = mBorderWidth;
        params.height = mBorderHeight;
        mShaderView.setLayoutParams(params);
        mShaderView.invalidate();
    }

    Animator.AnimatorListener mShaderAnimListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationCancel(Animator arg0) {
            mCurAnimatorSet = null;
        }

        @Override
        public void onAnimationEnd(Animator arg0) {
//            mShaderView.bringToFront();
            mCurAnimatorSet = null;
        }

        @Override
        public void onAnimationRepeat(Animator arg0) {
        }

        @Override
        public void onAnimationStart(Animator arg0) {
            mShaderView.bringToFront();
        }

    };
}
