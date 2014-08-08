package com.example.tvmetro.view;

import java.util.List;

import com.example.tvmetro.R;
import com.example.tvmetro.model.remote.Sub;
import com.example.tvmetro.utils.DensityUtil;
import com.example.tvmetro.view.MetroView.OrientationType;
import com.example.tvmetro.view.model.MetroItem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class HomePageHScrollView extends MetroScrollView {

    private Context mContext;

    private MetroView mMetroView;

    private int mLeftPadding;
    private int mTopPadding;
    private int mRightPadding;
    private int mBottomPadding;

    private int mCellWidth;
    private int mCellHeight;
    private int mGap;

    private int mVisiableRowCount;
    private int mVisiableColCount;

    public HomePageHScrollView(Context context) {
        this(context, null, 0);
    }

    public HomePageHScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageHScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(true);
        setSmoothScrollingEnabled(true);

        mContext = context;

        mMetroView = null;

        mCellWidth = DensityUtil.dip2px(mContext, 200);
        mCellHeight = DensityUtil.dip2px(mContext, 200);
        mGap = DensityUtil.dip2px(mContext, 5);

        mVisiableRowCount = 2;
        mVisiableColCount = 3;
    }

    public void setPagePadding(int left, int top, int right, int bottom) {
        mLeftPadding = DensityUtil.dip2px(mContext, left);
        mTopPadding = DensityUtil.dip2px(mContext, top);
        mRightPadding = DensityUtil.dip2px(mContext, right);
        mBottomPadding = DensityUtil.dip2px(mContext, bottom);
    }

    public void setCellWidth(int width) {
        mCellWidth = DensityUtil.dip2px(mContext, width);
    }

    public void setCellHeight(int height) {
        mCellHeight = DensityUtil.dip2px(mContext, height);
    }

    public void setGap(int gap) {
        mGap = DensityUtil.dip2px(mContext, gap);
    }

    public void setVisiableCount(int row, int col) {
        mVisiableRowCount = row;
        mVisiableColCount = col;
    }

    public void createView(List<Sub> listSub) {
        createMetroView();

        for (Sub sub : listSub) {
            Button btn = new Button(mContext);
            btn.setText(sub.getName());

            mMetroView.addMetroItem(new MetroItem(btn, sub.getRow(), sub
                    .getRowNum(), sub.getCol(), sub.getColNum()));
        }
    }

    private void createMetroView() {
        if (mMetroView != null) {
            return;
        }

        mMetroView = new MetroView(mContext);

        mMetroView.setOrientation(OrientationType.Horizontal);
        mMetroView.setRowHeight(mCellHeight);
        mMetroView.setColWidth(mCellWidth);
        mMetroView.setGap(mGap);
        mMetroView.setVisibleItems(mVisiableRowCount, mVisiableColCount);

        mMetroView.setPadding(mLeftPadding, mTopPadding, mRightPadding,
                mBottomPadding);

        /*
         * mMetroView.setPadding(DensityUtil.dip2px(mContext, 50),
         * DensityUtil.dip2px(mContext, 50), DensityUtil.dip2px(mContext, 50),
         * DensityUtil.dip2px(mContext, 50));
         */

        addView(mMetroView);
    }
}
