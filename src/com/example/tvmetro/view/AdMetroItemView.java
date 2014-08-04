package com.example.tvmetro.view;

import java.util.ArrayList;

import com.example.tvmetro.R;
import com.example.tvmetro.model.PageGlobalData;
import com.example.tvmetro.model.remote.ADStyle;
import com.example.tvmetro.model.remote.Describe;
import com.example.tvmetro.utils.ImageLoaderHelper;
import com.example.tvmetro.utils.Rotate3dAnimation;
import com.nostra13.universalimageloader.core.assist.FailReason;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import android.widget.ImageView.ScaleType;

public class AdMetroItemView extends MetroItemView {
    private static final int MSG_START_FLIP = 0;

    private ViewFlipper mAdFlipper;
    private ADStyle mCurAd;

    private Rotate3dAnimation mInAnim;
    private Rotate3dAnimation mOutAnim;

    public AdMetroItemView(Context context) {
        this(context, null, 0);
    }

    public AdMetroItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdMetroItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mAdFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
    }

    @Override
    public void setDescribe(PageGlobalData data, Describe describe) {
        if (describe == null || describe.getAdStyle() == null) {
            return;
        }

        this.mDescribe = describe;

        ArrayList<ADStyle> ads = describe.getAdStyle();
        for (ADStyle ad : ads) {
            AsyncImageView adView = new AsyncImageView(mContext);
            adView.setScaleType(ScaleType.FIT_XY);
            adView.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            adView.loadImage(data.getUrlHead() + ad.getPic(), mImgLoadListener);
            adView.setTag(false);
            mAdFlipper.addView(adView);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_START_FLIP:
                if (mAdFlipper.getWidth() == 0 || mAdFlipper.getHeight() == 0) {
                    mHandler.sendEmptyMessageDelayed(MSG_START_FLIP, 1000);
                } else {
                    startAdFlip();
                }
                break;
            }
        }
    };

    private ImageLoaderHelper.ImageLoaderHelperListener mImgLoadListener = new ImageLoaderHelper.ImageLoaderHelperListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                FailReason failReason) {
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                Bitmap loadedImage) {
            mHandler.removeMessages(MSG_START_FLIP);
            mHandler.sendEmptyMessageDelayed(MSG_START_FLIP, 1000);
            mImage.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }
    };

    private void startAdFlip() {
        ArrayList<ADStyle> ads = mDescribe.getAdStyle();
        create3DAnimation();
        mAdFlipper.setOutAnimation(mOutAnim);
        mAdFlipper.setInAnimation(mInAnim);
        mAdFlipper.setFlipInterval(ads.get(0).getAdShowTime());
        mAdFlipper.startFlipping();
    }

    private void create3DAnimation() {
        float halfWidth = mAdFlipper.getWidth() / 2.0f;
        float halfHeight = mAdFlipper.getHeight() / 2.0f;

        if (mInAnim == null) {
            Rotate3dAnimation in = new Rotate3dAnimation(270, 360, halfWidth,
                    halfHeight, 0, false, true);

            
            in.setDuration(600L);
            in.setFillAfter(false);
            in.setInterpolator(new AccelerateInterpolator());
            in.setStartOffset(300L);
            in.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    ArrayList<ADStyle> ads = mDescribe.getAdStyle();
                    mCurAd = ads.get(mAdFlipper.getDisplayedChild());
                }
            });

            mInAnim = in;
        }

        if (mOutAnim == null) {
            Rotate3dAnimation out = new Rotate3dAnimation(0, 90, halfWidth,
                    halfHeight, 0, true, true);

            /*
             * Rotate3dAnimation out = new Rotate3dAnimation(0, 90, halfWidth,
             * halfHeight, 0, false);
             */
            out.setDuration(300L);
            out.setFillAfter(false);
            out.setInterpolator(new AccelerateInterpolator());

            mOutAnim = out;
        }
    }
}
