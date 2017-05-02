package com.yang.keyboard.utils;

import android.view.View;

/**
 * Created by yangc on 2017/3/7.
 * E-Mail:1007181167@qq.com
 * Description：自定义键盘统一回调
 */

public interface OnKeyBoardLister {
    /**
     * @param text 发送文本
     **/
    void sendText(String text);

    /**
     * @param tag 表情tag
     * @param uri 大表情路径
     **/
    void sendUserDefEmoticon(String tag, String uri);

    /**
     * @param path 语音路径
     * @param time 语音时长
     **/
    void sendAudio(String path, int time);

    /**
     * @param ids 自定义选择id
     **/
    void sendMedia(int ids);

    /**
     * @param view 点击事件
     **/
    void clickAddBtn(View view);
}
