package com.example.tvmetro.view;

import java.util.ArrayList;

import com.example.tvmetro.R;
import com.example.tvmetro.utils.DensityUtil;
import com.example.tvmetro.view.model.MetroItem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MetroView extends RelativeLayout {

    public static enum OrientationType {
        All, Vertical, Horizontal
    };

    private Context mContext;

    /** Default count of visible items */
    private static final int DEF_VISIBLE_ROWS = 5;
    private static final int DEF_VISIBLE_COLS = 2;

    private OrientationType mOrientation = OrientationType.Horizontal;

    // Count of visible items
    protected int visibleRows = DEF_VISIBLE_ROWS;
    protected int visibleCols = DEF_VISIBLE_COLS;

    private int mCurRow = 0, mCurCol = 0;
    protected int mRowsCount = 0, mColsCount = 0;

    protected ArrayList<MetroItem> mMetroItems = new ArrayList<MetroItem>();

    private int mRowHeight, mColWidth;
    private int mGap;

    public MetroView(Context context) {
        this(context, null);
    }

    public MetroView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initViewGroup(context);
    }

    private void initViewGroup(Context context) {
        mContext = context;

        mColWidth = DensityUtil.dip2px(context, 200);
        mRowHeight = DensityUtil.dip2px(context, 200);
        mGap = DensityUtil.dip2px(context, 5);

        setClipChildren(false);
        setClipToPadding(false);
    }

    /**
     * set row and column count for visible item 0 equals to not change current
     * value others equal to
     * 
     * @param rowCount
     * @param colCount
     */
    public void setVisibleItems(int rowCount, int colCount) {

        if (rowCount < 0 || colCount < 0)
            throw new IllegalArgumentException("visible count < 0");

        if (rowCount != 0)
            visibleRows = rowCount;

        if (colCount != 0)
            visibleCols = colCount;
    }

    /**
     * set row height for cell
     * 
     * @param height
     */
    public void setRowHeight(int height) {
        mRowHeight = height;
    }

    /**
     * set col width for cell
     * 
     * @param width
     */
    public void setColWidth(int width) {
        mColWidth = width;
    }

    public void setGap(int gap) {
        mGap = gap;
    }

    public int getVisibleRows() {
        return visibleRows;
    }

    public int getVisibleCols() {
        return visibleCols;
    }

    public void addMetroItem(MetroItem item) {
        Rect r = getLayoutRect(item);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                r.width(), r.height());
        params.leftMargin = r.left;
        params.topMargin = r.top;
        mMetroItems.add(item);

        // addView(item.getMetroView(), params);
        addViewInLayout(item.getMetroView(), -1, params, true);

        adjustRowCol(item);
    }

    public boolean deleteMetroItem(MetroItem item) {

        boolean ret = false;

        if (mMetroItems.contains(item)) {
            mMetroItems.remove(item);
            removeView(item.getMetroView());
            ret = true;
        }

        mRowsCount = 0;
        mColsCount = 0;

        for (MetroItem mi : mMetroItems) {
            adjustRowCol(mi);
        }

        return ret;
    }

    public void clearMetroItem() {
        mMetroItems.clear();
        removeAllViews();
        removeAllViewsInLayout();

        mRowsCount = 0;
        mColsCount = 0;
    }

    public void setOrientation(OrientationType orientation) {
        mOrientation = orientation;
    }

    public OrientationType getOrientation() {
        return mOrientation;
    }

    public int getCurRow() {
        return mCurRow;
    }

    public int getCurCol() {
        return mCurCol;
    }

    private void adjustRowCol(MetroItem item) {
        // adjust rows count
        if (mRowsCount < item.getRow() + item.getRowSpan())
            mRowsCount = item.getRow() + item.getRowSpan();

        // adjust columns count
        if (mColsCount < item.getCol() + item.getColSpan())
            mColsCount = item.getCol() + item.getColSpan();
    }

    private Rect getLayoutRect(MetroItem item) {
        int left = (mColWidth + mGap) * item.getCol();
        int top = (mRowHeight + mGap) * item.getRow();
        int w = (mColWidth + mGap) * item.getColSpan() - mGap;
        int h = (mRowHeight + mGap) * item.getRowSpan() - mGap;

        return new Rect(left, top, left + w, top + h);
    }
}