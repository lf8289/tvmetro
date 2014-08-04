package com.example.tvmetro.model.remote;

import java.io.Serializable;

public class Sub implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bgColor;
    private String bgPic;
    private int colNum;
    private Describe describe;
    private boolean isFocus;
    private String name;
    private int rowNum;
    private String type;
    private String url;
    private int row;
    private int col;

    // private MetroItem mi;

    public String getBgColor() {
        return this.bgColor;
    }

    public String getBgPic() {
        return this.bgPic;
    }

    public int getColNum() {
        if (this.colNum <= 0.0F) {
            this.colNum = 2;
        }

        return this.colNum;
    }

    public Describe getDescribe() {
        return this.describe;
    }

    public String getName() {
        return this.name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getRowNum() {
        if (this.rowNum <= 0.0F) {
            this.rowNum = 2;
        }
        return this.rowNum;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    // public MetroItem getMetroItem() {
    // return this.mi;
    // }

    public boolean isFocus() {
        return this.isFocus;
    }

    public void setBgColor(String color) {
        this.bgColor = color;
    }

    public void setBgPic(String pic) {
        this.bgPic = pic;
    }

    public void setColNum(int num) {
        this.colNum = num;
    }

    public void setDescribe(Describe describe) {
        this.describe = describe;
    }

    public void setFocus(boolean focus) {
        this.isFocus = focus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRowNum(int num) {
        this.rowNum = num;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // public void setMetroItem(MetroItem mi) {
    // this.mi = mi;
    // }

    public String toString() {
        return "Sub [type=" + this.type + ", url=" + this.url + ", rowNum="
                + this.rowNum + ", colNum=" + this.colNum + ", isFocus="
                + this.isFocus + ", bgPic=" + this.bgPic + ", bgColor="
                + this.bgColor + ", describe=" + this.describe + "]";
    }
}
