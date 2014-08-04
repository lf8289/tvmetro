package com.example.tvmetro.model.remote;

import java.io.Serializable;

public class FontStyle implements Serializable {
    private static final long serialVersionUID = 1L;

    private String color = "0xff000000";
    private int height = 150;
    private int left = 0;
    private int size = 24;
    private int top = 0;
    private int width = 150;

    public String getColor() {
        return this.color;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLeft() {
        return this.left;
    }

    public int getSize() {
        return this.size;
    }

    public int getTop() {
        return this.top;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String toString() {
        return "FontStyle [left=" + this.left + ", top=" + this.top + "]";
    }
}
