package com.example.tvmetro.model.remote;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    private int layout_col;
    private int layout_row;
    private String name;
    private List<Sub> sub;
    private String url;

    public int getLayout_col() {
        if (this.layout_col <= 0) {
            this.layout_col = 2;
        }

        return this.layout_col;
    }

    public int getLayout_row() {
        if (this.layout_row <= 0) {
            this.layout_row = 2;
        }

        return this.layout_row;
    }

    public String getName() {
        return this.name;
    }

    public List<Sub> getSub() {
        return this.sub;
    }

    public String getUrl() {
        return this.url;
    }

    public void setLayout_col(int col) {
        this.layout_col = col;
    }

    public void setLayout_row(int row) {
        this.layout_row = row;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSub(List<Sub> sub) {
        this.sub = sub;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString() {
        return "Menu [name=" + this.name + ", url=" + this.url + ", sub="
                + this.sub + ", layout_row=" + this.layout_row
                + ", layout_col=" + this.layout_col + "]";
    }
}
