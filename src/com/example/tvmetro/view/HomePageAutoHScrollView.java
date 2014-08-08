package com.example.tvmetro.view;

import java.util.List;

import com.example.tvmetro.model.PageGlobalData;
import com.example.tvmetro.model.remote.Sub;
import com.example.tvmetro.utils.DensityUtil;
import com.example.tvmetro.view.MetroView.OrientationType;
import com.example.tvmetro.view.model.MetroItem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class HomePageAutoHScrollView extends MetroScrollView {

    private Context mContext;

    private AutoMetroView mMetroView;
    private HomePageFrameLayout mPageLayout;

    private int mLeftPadding;
    private int mTopPadding;
    private int mRightPadding;
    private int mBottomPadding;

    private int mCellWidth;
    private int mCellHeight;
    private int mGap;

    private int mVisiableRowCount;
    private int mVisiableColCount;

    private HomePageLayoutFactory mHpFactory;

    public HomePageAutoHScrollView(Context context) {
        this(context, null, 0);
    }

    public HomePageAutoHScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomePageAutoHScrollView(Context context, AttributeSet attrs,
            int defStyle) {
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

        mHpFactory = HomePageLayoutFactory.getInstance(context);
    }

    public void setPageGlobalData(PageGlobalData data) {
        mHpFactory.setPageGlobalData(data);
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
            HomePageCellView iv = new HomePageCellView(mContext);
            MetroItem mi = new MetroItem(iv, 0, sub.getRowNum(), 0,
                    sub.getColNum());
            MetroItemView miv = mHpFactory.getMetroItemView(sub);
            miv.setOnClickListener(mMivClickListener);
            miv.setTag(sub);
            iv.setMetroItem(mi, miv);

            mMetroView.addMetroItem(mi);
            iv.setOnFocusChangeListener(mPageLayout);
        }
    }

    private OnClickListener mMivClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            if (arg0 instanceof MetroItemView) {
                MetroItemView miv = (MetroItemView) arg0;
                Sub sub = (Sub) miv.getTag();
                Log.d("LIF", "sub.getName() = " + sub.getName());
            }
        }

    };

    private void createMetroView() {
        if (mMetroView != null) {
            return;
        }

        mMetroView = new AutoMetroView(mContext);

        mMetroView.setOrientation(OrientationType.Horizontal);
        mMetroView.setRowHeight(mCellHeight);
        mMetroView.setColWidth(mCellWidth);
        mMetroView.setGap(mGap);
        mMetroView.setVisibleItems(mVisiableRowCount, mVisiableColCount);

        mMetroView.setPadding(mLeftPadding, mTopPadding, mRightPadding,
                mBottomPadding);

        mPageLayout = new HomePageFrameLayout(mContext);
        mPageLayout.addView(mMetroView);
        addView(mPageLayout);
    }
}
