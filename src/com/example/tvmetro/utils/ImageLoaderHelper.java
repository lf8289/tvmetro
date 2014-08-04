package com.example.tvmetro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageLoaderHelper {
    private static final String TAG = ImageLoaderHelper.class.getName();

    private static ImageLoaderHelper instance = null;
    private DisplayImageOptions options;

    protected ImageLoader imageLoader;

    private ImageLoaderHelper(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder().showImageForEmptyUri(null)
                .showImageOnFail(null).resetViewBeforeLoading(true)
                .cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300)).build();
    }

    public static ImageLoaderHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ImageLoaderHelper(context);
        }

        return instance;
    }

    public void displayImage(String url, ImageView view) {
        imageLoader.displayImage(url, view, options);
    }

    public void displayImage(String url, ImageView view,
            ImageLoaderHelperListener listener) {
        imageLoader.displayImage(url, view, options, listener);
    }

    public static class ImageLoaderHelperListener implements ImageLoadingListener {
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
    }
}
