package com.example.tvmetro.view;

import com.example.tvmetro.model.LayoutFactory;
import com.example.tvmetro.model.PageGlobalData;
import com.example.tvmetro.model.remote.Sub;
import com.example.tvmetro.utils.ImageLoaderHelper;
import com.nostra13.universalimageloader.core.assist.FailReason;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;

public class HomePageLayoutFactory {

    private static HomePageLayoutFactory sInstance;

    private PageGlobalData mPageData;
    private LayoutInflater mLayoutInflater;

    private HomePageLayoutFactory(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public static HomePageLayoutFactory getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HomePageLayoutFactory(context);
        }

        return sInstance;
    }

    public void setPageGlobalData(PageGlobalData data) {
        mPageData = data;
    }

    public MetroItemView getMetroItemView(Sub sub) {
        MetroItemView view = (MetroItemView) mLayoutInflater.inflate(
                getLayoutFile(sub.getType()), null);
        setupData(view, sub);

        return view;
    }

    private int getLayoutFile(String type) {
        return LayoutFactory.getInstance().getLayout(type);
    }

    private void setupData(MetroItemView view, Sub sub) {
        // setTagAndClickListener(view, sub);
        setBackground(view, sub);
    }

    /*
     * private void setTagAndClickListener(MetroItemView panel, Sub sub) {
     * ImageButton proxy = panel.getProxy(); if (proxy == null) { return; }
     * 
     * proxy.setTag(sub); // panel.setTag(sub);
     * proxy.setOnClickListener(onClickListener);
     * 
     * AsyncImageView image = panel.getImage(); if (image != null) {
     * image.setTag(mi); } }
     */

    private void setBackground(MetroItemView view, Sub sub) {
        AsyncImageView image = view.getImage();

        switch (LayoutFactory.BgTypeEnum.fromValue(sub.getType())) {
        case IMG:
            view.setName(sub.getName());
            image.loadImage(mPageData.getUrlHead() + sub.getBgPic(),
                    mImgLoadListener);
            break;

        case COLOR:
            view.setName(sub.getName());
            break;

        case ACTION:
            view.setName(sub.getName());
            view.setDescribe(mPageData, sub.getDescribe());
            image.loadImage(mPageData.getUrlHead() + sub.getBgPic(),
                    mImgLoadListener);
            break;

        case VIDEO:
            view.setName(sub.getName());
            break;

        case APP:
            break;

        case ADSHOW:
            view.setDescribe(mPageData, sub.getDescribe());
            break;
        }
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
            if (view instanceof AsyncImageView) {
                View hpc = (View) view.getParent().getParent();
                hpc.invalidate();
            }
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }
    };
}
