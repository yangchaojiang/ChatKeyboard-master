package com.yang.keyboard_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yang.keyboard.ChatKeyboardLayout;
import com.yang.keyboard.KeyboardFragment;
import com.yang.keyboard.view.RecordingLayout;
import com.yang.keyboard.media.MediaBean;

import java.util.ArrayList;

/**
 * Created by yangc on 2017/3/15.
 * E-Mail:1007181167@qq.com
 * Description：
 */

public class UserFragment extends KeyboardFragment {

    @Override
    protected ArrayList<MediaBean> intiData() {
        ArrayList<MediaBean> popupModels = new ArrayList<>();
        popupModels.add(new MediaBean(0, com.yang.keyboard.R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(1, com.yang.keyboard.R.drawable.icon_photo, "照片", this));

        return popupModels;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.simple_fragment_keybord_layouts, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        keyboardLayout = (ChatKeyboardLayout) view.findViewById(com.yang.keyboard.R.id.kv_bar);
        rlRecordArea = (RecordingLayout) view.findViewById(com.yang.keyboard.R.id.recording_area);
        ArrayList<MediaBean> popupModels = intiData();
        keyboardLayout.showMedias(popupModels);
        keyboardLayout.showEmoBtn(false);
        keyboardLayout.setOnKeyBoardBarListener(this);
    }
    ChatKeyboardLayout getChatKeyboardLayout() {
        return keyboardLayout;
    }
}