package com.mywx.resp;

/**
 * Created by wangzy on 2017/6/2.
 */
public class ImageMessage extends BaseMessage {
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
