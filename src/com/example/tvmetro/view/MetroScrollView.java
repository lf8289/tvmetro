package com.example.tvmetro.view;

//import com.vlctech.metruuiengine.R;
//import android.media.SoundPool;

import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;

import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.HorizontalScrollView;

public class MetroScrollView extends HorizontalScrollView {

    private static final int EXTRA_DELTA = 200;

    private Context mContext;

    AudioManager mAudioMgr;

    public MetroScrollView(Context context) {
        this(context, null, 0);
    }

    public MetroScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetroScrollView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);

        mContext = context;
        mAudioMgr = (AudioManager) mContext
                .getSystemService(mContext.AUDIO_SERVICE);

        this.setWillNotDraw(true);
        this.setSmoothScrollingEnabled(true);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled) {
            mAudioMgr.playSoundEffect(AudioManager.FX_KEY_CLICK);
        }

        return handled;
    }

    @Override
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0)
            return 0;

        int width = getWidth();
        int screenLeft = getScrollX();
        int screenRight = screenLeft + width;

        int fadingEdge = getHorizontalFadingEdgeLength();

        // leave room for left fading edge as long as rect isn't at very left
        if (rect.left > 0) {
            screenLeft += fadingEdge;
        }

        // leave room for right fading edge as long as rect isn't at very right
        if (rect.right < getChildAt(0).getWidth()) {
            screenRight -= fadingEdge;
        }

        int scrollXDelta = 0;

        if (rect.right > screenRight - EXTRA_DELTA && rect.left > screenLeft) {
            // need to move right to get it in view: move right just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.width() > width) {
                // just enough to get screen size chunk on
                scrollXDelta += (rect.left - screenLeft);
            } else {
                // get entire rect at right of screen
                scrollXDelta += (rect.right - screenRight + EXTRA_DELTA);
            }

            // make sure we aren't scrolling beyond the end of our content
            int right = getChildAt(0).getRight();
            int distanceToRight = right - screenRight;
            scrollXDelta = Math.min(scrollXDelta, distanceToRight);

        } else if (rect.left < screenLeft + EXTRA_DELTA
                && rect.right < screenRight) {
            // need to move right to get it in view: move right just enough so
            // that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.width() > width) {
                // screen size chunk
                scrollXDelta -= (screenRight - rect.right);
            } else {
                // entire rect at left
                scrollXDelta -= (screenLeft - rect.left + EXTRA_DELTA);
            }

            // make sure we aren't scrolling any further than the left our
            // content
            scrollXDelta = Math.max(scrollXDelta, -getScrollX());
        }

        return scrollXDelta;
    }

}
