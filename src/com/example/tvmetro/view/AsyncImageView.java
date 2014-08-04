package com.example.tvmetro.view;

import com.example.tvmetro.utils.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.assist.FailReason;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class AsyncImageView extends ImageView {

    private ImageLoaderHelper loadHelper;

    public AsyncImageView(Context context) {
        this(context, null, 0);
    }

    public AsyncImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AsyncImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        loadHelper = ImageLoaderHelper.getInstance(getContext());
    }

    public void loadImage(String url) {
        loadHelper.displayImage(url, this, mImgLoadListener);
    }

    public void loadImage(String url,
            ImageLoaderHelper.ImageLoaderHelperListener listener) {
        loadHelper.displayImage(url, this, listener);
    }

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
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }
    };
}
