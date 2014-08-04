package com.example.tvmetro.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class BitmapUtil {
    public static Bitmap convertViewToBitmap(View view) {
        /*
         * view.setDrawingCacheEnabled(true);
         * view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
         * MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
         * view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
         */
        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 把View绘制到Bitmap上
     * 
     * @param view
     *            需要绘制的View
     * @param width
     *            该View的宽度
     * @param height
     *            该View的高度
     * @return 返回Bitmap对象
     */
    public static Bitmap getBitmapFromView(View view) {
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        int widthSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(height,
                View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    /**
     * 获得带倒影的图片
     * 
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectedImage(View view, int reflectHeight) {
        Bitmap bitmap = getBitmapFromView(view);

        // int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height
                - reflectHeight, view.getMeasuredWidth(), reflectHeight,
                matrix, false);
        Bitmap newBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                reflectHeight, Config.ARGB_8888);

        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(reflectionImage, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, reflectHeight,
                0x70ffffff, 0x00ffffff, TileMode.MIRROR);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, 0, view.getMeasuredWidth(), reflectHeight, paint);
        
        reflectionImage.recycle();
        reflectionImage = null;

        return newBitmap;
    }

}
