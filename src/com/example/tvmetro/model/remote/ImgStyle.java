package com.example.tvmetro.model.remote;

import java.io.Serializable;

public class ImgStyle implements Serializable {
    private static final long serialVersionUID = 1L;

    private int height = 50;
    private int left = 0;
    private int top = 0;
    private int width = 50;

    public int getHeight() {
        return this.height;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.top;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String toString() {
        return "ImgStyle [left=" + this.left + ", top=" + this.top + ", width="
                + this.width + ", height=" + this.height + "]";
    }
}
