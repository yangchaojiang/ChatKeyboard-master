package com.yang.keyboard_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yang.keyboard.ChatKeyboardLayout;
import com.yang.keyboard.KeyboardFragment;
import com.yang.keyboard.RecordingLayout;
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
        popupModels.add(new MediaBean(2, com.yang.keyboard.R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(3, com.yang.keyboard.R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(4, com.yang.keyboard.R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(5, com.yang.keyboard.R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(6, com.yang.keyboard.R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(7, com.yang.keyboard.R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(8, com.yang.keyboard.R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(9, com.yang.keyboard.R.drawable.icon_photo, "照片", this));
        return popupModels;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keyboardLayout = (ChatKeyboardLayout) view.findViewById(com.yang.keyboard.R.id.kv_bar);
        rlRecordArea = (RecordingLayout) view.findViewById(com.yang.keyboard.R.id.recording_area);
        ArrayList<MediaBean> popupModels = intiData();
        keyboardLayout.showMedias(popupModels);
        keyboardLayout.setOnKeyBoardBarListener(this);
    }
}