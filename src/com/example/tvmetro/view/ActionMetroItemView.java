package com.example.tvmetro.view;

import java.util.ArrayList;

import com.example.tvmetro.R;
import com.example.tvmetro.model.PageGlobalData;
import com.example.tvmetro.model.remote.ActionStyle;
import com.example.tvmetro.model.remote.Describe;
import com.example.tvmetro.utils.DensityUtil;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ActionMetroItemView extends MetroItemView {

    private final int ACT_OFFSET;

    private AsyncImageView mLeftImage;
    private AsyncImageView mRightImage;

    public ActionMetroItemView(Context context) {
        this(context, null, 0);
    }

    public ActionMetroItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionMetroItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ACT_OFFSET = DensityUtil.dip2px(context, 30);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLeftImage = (AsyncImageView) findViewById(R.id.ation_left);
        mRightImage = (AsyncImageView) findViewById(R.id.ation_right);
    }

    @Override
    public void setDescribe(PageGlobalData data, Describe describe) {
        if (describe == null || describe.getActionStyle() == null) {
            return;
        }

        this.mDescribe = describe;

        ActionStyle action = describe.getActionStyle();
        if (action.getLeftPic() != null) {
            mLeftImage.setVisibility(View.VISIBLE);
            mLeftImage.loadImage(data.getUrlHead() + action.getLeftPic());
        }
        if (action.getRightPic() != null) {
            mRightImage.setVisibility(View.VISIBLE);
            mRightImage.loadImage(data.getUrlHead() + action.getRightPic());
        }
    }
    
    @Override
    protected ArrayList<Animator> getAnimatorList(boolean hasFocus) {
        ArrayList<Animator> animators = new ArrayList<Animator>();

        float leftFromX, leftToX, leftFromY, leftToY;
        float rightFromX, rightToX, rightFromY, rightToY;

        if (hasFocus) {
            /*
             * leftFromX = 0; leftToX = -25; leftFromY = 0; leftToY = 0;
             */

            leftFromX = 0;
            leftToX = 0;
            leftFromY = 0;
            leftToY = -ACT_OFFSET;

            rightFromX = 0;
            rightToX = -ACT_OFFSET;
            rightFromY = 0;
            rightToY = 0;
        } else {
            /*
             * leftFromX = -25; leftToX = 0; leftFromY = 0; leftToY = 0;
             */

            leftFromX = 0;
            leftToX = 0;
            leftFromY = -ACT_OFFSET;
            leftToY = 0;

            rightFromX = -ACT_OFFSET;
            rightToX = 0;
            rightFromY = 0;
            rightToY = 0;
        }

        if (mLeftImage.getVisibility() == View.VISIBLE) {
            animators.add(ObjectAnimator.ofFloat(mLeftImage, "translationX",
                    leftFromX, leftToX));
            animators.add(ObjectAnimator.ofFloat(mLeftImage, "translationY",
                    leftFromY, leftToY));
        }

        if (mRightImage.getVisibility() == View.VISIBLE) {
            animators.add(ObjectAnimator.ofFloat(mRightImage, "translationX",
                    rightFromX, rightToX));
            animators.add(ObjectAnimator.ofFloat(mRightImage, "translationY",
                    rightFromY, rightToY));
        }

        return animators;
    }

}
