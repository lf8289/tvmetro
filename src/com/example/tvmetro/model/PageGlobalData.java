package com.example.tvmetro.model;

import java.io.Serializable;
import java.util.List;

import com.example.tvmetro.model.local.IData;
import com.example.tvmetro.model.local.ModelImpl;
import com.example.tvmetro.model.remote.Menu;

public class PageGlobalData implements IData, Serializable {
    private static final long serialVersionUID = 1L;

    private int canvas_Left;
    private int canvas_Top;
    private int cell_height;
    private int cell_width;
    private int internal;
    private int layout_col;
    private int layout_row;
    private List<Menu> menu;
    private String url_head;

    public int getCanvas_Left() {
        if (this.canvas_Left <= 0) {
            this.canvas_Left = 70;
        }

        return this.canvas_Left;
    }

    public int getCanvas_Top() {
        if (this.canvas_Top <= 0) {
            this.canvas_Top = 10;
        }

        return this.canvas_Top;
    }

    public int getCell_height() {
        if (this.cell_height <= 0) {
            this.cell_height = 100;
        }

        return this.cell_height;
    }

    public int getCell_width() {
        return this.cell_width;
    }

    public int getInternal() {
        return this.internal;
    }

    public int getLayout_col() {
        if (this.layout_col <= 0) {
            this.layout_col = 10;
        }

        return this.layout_col;
    }

    public int getLayout_row() {
        if (this.layout_row <= 0) {
            this.layout_row = 4;
        }

        return this.layout_row;
    }

    public List<Menu> getMenu() {
        return this.menu;
    }

    public String getUrlHead() {
        return this.url_head;
    }

    public void setCanvas_Left(int left) {
        this.canvas_Left = left;
    }

    public void setCanvas_Top(int top) {
        this.canvas_Top = top;
    }

    public void setCell_height(int height) {
        this.cell_height = height;
    }

    public void setCell_width(int width) {
        this.cell_width = width;
    }

    public void setInternal(int internal) {
        this.internal = internal;
    }

    public void setLayout_col(int col) {
        this.layout_col = col;
    }

    public void setLayout_row(int row) {
        this.layout_row = row;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public static PageGlobalData getFromJSon(String content) {
        IData data = new ModelImpl(PageGlobalData.class).doParser(content);
        boolean success = false;
        PageGlobalData pgd = null;

        if (data != null) {
            success = data instanceof PageGlobalData;
            if (success) {
                pgd = (PageGlobalData) data;
            }
        }

        return pgd;
    }

    public String toString() {
        return "PageEntity [layout_row=" + this.layout_row + ", layout_col="
                + this.layout_col + ", canvas_Left=" + this.canvas_Left
                + ", canvas_Top=" + this.canvas_Top + ", cell_width="
                + this.cell_width + ", cell_height=" + this.cell_height
                + ", internal=" + this.internal + ", menu=" + this.menu + "]";
    }
}
