package com.example.tvmetro.view;

import java.util.ArrayList;

import com.example.tvmetro.R;
import com.example.tvmetro.utils.BitmapUtil;
import com.example.tvmetro.utils.DensityUtil;
import com.example.tvmetro.view.model.MetroItem;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class HomePageCellView extends FrameLayout implements
        View.OnFocusChangeListener {

    private FrameLayout.LayoutParams sFillParams = new FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    private Context mContext;
    private MetroItemView mMetroItemView;

    private boolean mIsRelection;
    private int mRelectionHeight;
    private Bitmap mFadedBitmap;

    private AnimatorSet mAnimatorSet;
    private ArrayList<Animator> mZoomInAnimator;
    private ArrayList<Animator> mZoomOutAnimator;

    private View.OnFocusChangeListener mFocusListener;

    public HomePageCellView(Context context) {
        this(context, null, 0);
    }

    public HomePageCellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageCellView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;

        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);

        mRelectionHeight = DensityUtil.dip2px(context, 80);
        // mWhiteBorder.setImageResource(R.drawable.white_border);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(250);

        mZoomInAnimator = new ArrayList<Animator>();
        mZoomInAnimator.add(ObjectAnimator.ofFloat(this, View.SCALE_X, 1.105F,
                1F));
        mZoomInAnimator.add(ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.105F,
                1F));

        mZoomOutAnimator = new ArrayList<Animator>();
        mZoomOutAnimator.add(ObjectAnimator.ofFloat(this, View.SCALE_X, 1F,
                1.105F));
        mZoomOutAnimator.add(ObjectAnimator.ofFloat(this, View.SCALE_Y, 1F,
                1.105F));
    }

    // @Override
    // protected void onDraw(Canvas canvas) {
    // if (mMetroItemView != null && mMetroItemView.isReflection()
    // && mMetroItemView.getImage().getDrawable() != null) {
    // createReflection(canvas);
    // }
    // }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);

        if (mMetroItemView != null && mMetroItemView.isReflection()
                && mMetroItemView.getImage().getDrawable() != null) {
            createReflection(canvas);
        }

        return ret;
    }

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener listener) {
        mFocusListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();

        if (mMetroItemView.isReflection()) {
            setMeasuredDimension(measuredWidth, measureHeight
                    + mRelectionHeight);
        } else {
            setMeasuredDimension(measuredWidth, measureHeight);
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }
        
        if (mFocusListener != null) {
            mFocusListener.onFocusChange(this, hasFocus);
        }

        mAnimatorSet = new AnimatorSet();

//        ArrayList<Animator> objanimlist = new ArrayList<Animator>();
        ArrayList<Animator> objanimlist = mMetroItemView.getAnimatorList(hasFocus);
        if (objanimlist == null) {
            objanimlist = new ArrayList<Animator>();
        }

        if (hasFocus) {
            bringToFront();
            for (Animator anim : mZoomOutAnimator) {
                objanimlist.add(anim);
            }
        } else {
            for (Animator anim : mZoomInAnimator) {
                objanimlist.add(anim);
            }
        }

        clearAnimation();
        mAnimatorSet.playTogether(objanimlist);
        mAnimatorSet.setDuration(250);
        mAnimatorSet.setInterpolator(new DecelerateInterpolator());
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorSet = null;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimatorSet = null;
            }
        });
        mAnimatorSet.start();
        // this.invalidate();

//        if (mFocusListener != null) {
//            mFocusListener.onFocusChange(this, hasFocus);
//        }
    }

    public void setMetroItem(MetroItem item, MetroItemView view) {
        // mMetroItemView = new MetroItemView(mContext);
        mMetroItemView = view;
        mMetroItemView.setMetroItem(item);
        mMetroItemView.setOnFocusChangeListener(this);
        addViewInLayout(mMetroItemView, -1, sFillParams, true);

        // mMetroItemView.setBackgroundColor(0XFF007600);
    }

    public MetroItemView getMetroItemView() {
        return mMetroItemView;
    }

    public void setMargin(int margin) {
        sFillParams.setMargins(margin, margin, margin, margin);
    }

    public void setReflectHeight(int height) {
        mRelectionHeight = DensityUtil.dip2px(mContext, height);
    }

    public int getReflectHeight() {
        return mRelectionHeight;
    }

    private void createReflection(Canvas canvas) {
        if (!mIsRelection) {
            this.postInvalidate();
            mIsRelection = true;
            return;
        }

        TextView name = mMetroItemView.getNameView();
        boolean isTxtShow = name.getVisibility() == View.VISIBLE;
        if (isTxtShow) {
            name.setVisibility(View.INVISIBLE);
        }

        if (mFadedBitmap == null) {
            mFadedBitmap = BitmapUtil.createReflectedImage(mMetroItemView,
                    mRelectionHeight);
        }

        if (isTxtShow) {
            name.setVisibility(View.VISIBLE);
        }

        canvas.drawBitmap(mFadedBitmap, 0, mMetroItemView.getMeasuredHeight(),
                null);
    }
}
