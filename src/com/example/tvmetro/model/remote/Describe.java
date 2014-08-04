package com.example.tvmetro.model.remote;

import java.io.Serializable;
import java.util.ArrayList;

public class Describe implements Serializable {
    private static final long serialVersionUID = 1L;

    private ActionStyle action_style;
    private ArrayList<ADStyle> ad_style;
    private FontStyle font_style;
    private ImgStyle img_style;
    public boolean isDirty = false;
    private String menu_dataKey;
    private String menu_name;
    private String menu_pic;

    public ActionStyle getActionStyle() {
        return this.action_style;
    }

    public ArrayList<ADStyle> getAdStyle() {
        return this.ad_style;
    }

    public FontStyle getFont_style() {
        return this.font_style;
    }

    public ImgStyle getImg_style() {
        return this.img_style;
    }

    public String getMenuDataKey() {
        return this.menu_dataKey;
    }

    public String getMenuName() {
        return this.menu_name;
    }

    public String getMenuPicPath() {
        return this.menu_pic;
    }

    public void setFont_style(FontStyle style) {
        this.font_style = style;
    }

    public void setImg_style(ImgStyle style) {
        this.img_style = style;
    }

    public void setMenuDatakey(String datakey) {
        this.menu_dataKey = datakey;
    }

    public void setMenuName(String name) {
        this.menu_name = name;
    }

    public void setMenuPicPath(String path) {
        this.menu_pic = path;
    }

    public String toString() {
        return "Describe [menu_name=" + this.menu_name + ", menu_pic="
                + this.menu_pic + ", font_style=" + this.font_style
                + ", img_style=" + this.img_style + "]";
    }
}
