package com.example.tvmetro.view;

import java.util.HashMap;

import com.example.tvmetro.R;
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
    private HashMap<String, Integer> layoutMap = new HashMap<String, Integer>();

    public enum BgTypeEnum {
        IMG("img"), COLOR("color"), ACTION("action"), VIDEO("video"), APP("app"), ADSHOW(
                "adshow");

        public final String value;

        private BgTypeEnum(String value) {
            this.value = value;
        }

        public static BgTypeEnum fromValue(String value) {
            for (BgTypeEnum style : BgTypeEnum.values()) {
                if (value.equalsIgnoreCase(style.value)) {
                    return style;
                }
            }
            return null;
        }
    }

    private HomePageLayoutFactory(Context context) {
        layoutMap.put("img",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));

        layoutMap.put("action",
                Integer.valueOf(R.layout.layout_templet_action_rect));

        layoutMap.put("adshow",
                Integer.valueOf(R.layout.layout_templet_h_largeadshow_rect));

        // TODO: to add these layout
        layoutMap.put("color",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));
        layoutMap.put("video",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));
        layoutMap.put("app",
                Integer.valueOf(R.layout.layout_templet_h_large_rect));

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
        int layout = -1;

        if (layoutMap.containsKey(type)) {
            layout = layoutMap.get(type);
        }

        return layout;
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

        switch (BgTypeEnum.fromValue(sub.getType())) {
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

        @SuppressWarnings("deprecation")
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
