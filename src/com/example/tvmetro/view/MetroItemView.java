package com.example.tvmetro.view;

import java.util.ArrayList;

import com.example.tvmetro.R;
import com.example.tvmetro.model.PageGlobalData;
import com.example.tvmetro.model.remote.Describe;
import com.example.tvmetro.view.model.MetroItem;

import android.animation.Animator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MetroItemView extends FrameLayout {

    public static final FrameLayout.LayoutParams sFillParams = new FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    protected Context mContext;
    private MetroItem mMetroItem;

    protected AsyncImageView mImage;
    protected TextView mName;

    protected Describe mDescribe;

    private boolean mIsNameVisiable;

    public MetroItemView(Context context) {
        this(context, null, 0);
    }

    public MetroItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        
        mContext = context;

        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        setClipChildren(false);
        setClipToPadding(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mImage = (AsyncImageView) findViewById(R.id.image);
        mName = (TextView) findViewById(R.id.name);
    }

    public void setMetroItem(MetroItem item) {
        mMetroItem = item;
    }

    public MetroItem getMetroItem() {
        return mMetroItem;
    }

    public void addViewInner(View child, int index, LayoutParams params,
            boolean preventRequestLayout) {
        addViewInLayout(child, index, params, preventRequestLayout);
    }

    public View getView(Context context) {
        return mMetroItem.getMetroView();
    }

    public boolean isReflection() {
        return mMetroItem.getIsLayoutBottom();
    }

    public AsyncImageView getImage() {
        return mImage;
    }

    public TextView getNameView() {
        return mName;
    }

    /**
     * override by sub class
     * 
     * @return Animator list
     */
    protected ArrayList<Animator> getAnimatorList(boolean hasFocus) {
        return null;
    }

    public void setName(String name) {
        mName.setText(name);
        mIsNameVisiable = TextUtils.isEmpty(name) ? false : true;
    }

    /**
     * override by sub class
     */
    public void setDescribe(PageGlobalData data, Describe describe) {
        this.mDescribe = describe;
    }
}
