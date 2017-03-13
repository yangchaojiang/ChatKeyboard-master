package com.yang.keyboard.media;


/**
 * Created by yangc on 2017/3/7.
 * E-Mail:1007181167@qq.com
 *
 * @deprecated 自定义选择实体类
 **/
public class MediaBean {
    private int id;
    private int drawableId;
    private String text;
    private MediaListener mediaListener;

    public MediaBean(int id, int drawableId, String text, MediaListener mediaListener) {
        this.id = id;
        this.drawableId = drawableId;
        this.text = text;
        this.mediaListener = mediaListener;
    }

    public int getId() {
        return id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getText() {
        return text;
    }

    public MediaListener getMediaListener() {
        return mediaListener;
    }
}
