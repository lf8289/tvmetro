package com.example.tvmetro.model.remote;

public class ActionStyle {
    private String left_pic;
    private String right_pic;
    private int type = 0;

    public String getLeftPic() {
        return this.left_pic;
    }

    public String getRightPic() {
        return this.right_pic;
    }

    public int getType() {
        return this.type;
    }

    public void setLeftPic(String uri) {
        this.left_pic = uri;
    }

    public void setRightPic(String uri) {
        this.right_pic = uri;
    }
}
