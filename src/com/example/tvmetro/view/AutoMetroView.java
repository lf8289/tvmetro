package com.example.tvmetro.view;

import java.util.HashMap;

import com.example.tvmetro.view.model.MetroItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class AutoMetroView extends MetroView {

    private boolean isHorizontal = false;

    private int mRows = 0, mCols = 0;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Boolean> mMetroPool = new HashMap<Integer, Boolean>();

    public AutoMetroView(Context context) {
        super(context);
        initViewGroup(context);
    }

    public AutoMetroView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewGroup(context);
    }

    public AutoMetroView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViewGroup(context);
    }

    private void initViewGroup(Context context) {
        mRows = 0;
        mCols = 0;

        mMetroPool.clear();
    }

    private int[] getPosition(View v, int rowspan, int colspan) {

        if (v == null)
            throw new IllegalArgumentException("invalid item");

        int[] position = new int[2];
        if (isHorizontal) {
            // check rowspan
            if (rowspan > visibleRows)
                throw new IllegalArgumentException("invalid row span");

            getPositionByCol(v, rowspan, colspan, position);
        } else {
            // check colspan
            if (colspan > visibleCols)
                throw new IllegalArgumentException("invalid col span");

            getPositionByRow(v, rowspan, colspan, position);
        }

        return position;
    }

    private void updateItemPosition(MetroItem item) {
        int[] position = getPosition(item.getMetroView(), item.getRowSpan(),
                item.getColSpan());
        addMark(position[0], item.getRowSpan(), position[1], item.getColSpan());
        item.setPosition(position[0], position[1]);
    }

    private void getPositionByRow(View v, int rowspan, int colspan,
            int[] position) {

        boolean isAppended = false;

        // id = x + cols * y
        for (int y = 0; y < mRows; y++) {
            for (int x = 0; x < visibleCols; x++) {

                // check span
                if (x + colspan > visibleCols)
                    break;

                boolean hasSpace = true;
                for (int row = 0; row < rowspan; row++) {
                    for (int col = 0; col < colspan; col++) {

                        int id = (x + col) + visibleCols * (y + row);

                        if (mMetroPool.containsKey(id)) {
                            hasSpace = false;
                            break;
                        }
                    }

                    if (!hasSpace)
                        break;
                }

                if (hasSpace) {

                    position[0] = y;
                    position[1] = x;

                    isAppended = true;
                    break;
                }
            }

            if (isAppended) {
                break;
            }
        }

        if (!isAppended) {
            position[0] = mRows;
            position[1] = 0;
        }
    }

    private void getPositionByCol(View v, int rowspan, int colspan,
            int[] position) {

        boolean isAppended = false;

        // id = x * rows + y
        for (int x = 0; x < mCols; x++) {
            for (int y = 0; y < visibleRows; y++) {

                // check span
                if (y + rowspan > visibleRows)
                    break;

                boolean hasSpace = true;
                for (int row = 0; row < rowspan; row++) {
                    for (int col = 0; col < colspan; col++) {

                        int id = (x + col) * visibleRows + (y + row);

                        if (mMetroPool.containsKey(id)) {
                            hasSpace = false;
                            break;
                        }
                    }

                    if (!hasSpace)
                        break;
                }

                if (hasSpace) {

                    position[0] = y;
                    position[1] = x;

                    isAppended = true;
                    break;
                }
            }

            if (isAppended) {
                break;
            }
        }

        if (!isAppended) {
            position[0] = 0;
            position[1] = mCols;
        }
    }

    private void addMark(int row, int rowspan, int col, int colspan) {

        if (isHorizontal)
            mCols = Math.max(mCols, col + colspan);
        else
            mRows = Math.max(mRows, row + rowspan);

        // add flag
        for (int x = col; x < col + colspan; x++)
            for (int y = row; y < row + rowspan; y++) {

                int id = 0;

                if (isHorizontal)
                    id = x * visibleRows + y;
                else
                    id = x + visibleCols * y;

                mMetroPool.put(id, true);
            }
    }

    @Override
    public void setVisibleItems(int rowCount, int colCount) {
        super.setVisibleItems(rowCount, colCount);

        // update item position
        initViewGroup(getContext());
        for (MetroItem item : mMetroItems) {
            updateItemPosition(item);
        }

        // update count
        if (isHorizontal)
            mColsCount = mCols;
        else
            mRowsCount = mRows;
    }

    @Override
    public void setOrientation(OrientationType orientation) {

        if (orientation != OrientationType.Horizontal
                && orientation != OrientationType.Vertical)
            throw new IllegalArgumentException("invalid orientation type");

        super.setOrientation(orientation);

        if (orientation == OrientationType.Horizontal)
            isHorizontal = true;
        else if (orientation == OrientationType.Vertical)
            isHorizontal = false;
    }

    @Override
    public void addMetroItem(MetroItem item) {
        int[] position = getPosition(item.getMetroView(), item.getRowSpan(),
                item.getColSpan());
        addMark(position[0], item.getRowSpan(), position[1], item.getColSpan());
        item.setPosition(position[0], position[1]);
        super.addMetroItem(item);

        if (isHorizontal && (item.getRow() + item.getRowSpan()) == visibleRows) {
            item.setIsLayoutBottom(true);
        }
    }
}
