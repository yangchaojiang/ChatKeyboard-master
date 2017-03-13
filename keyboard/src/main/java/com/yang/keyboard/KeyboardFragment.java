package com.yang.keyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.yang.keyboard.audio.AudioManger;
import com.yang.keyboard.media.MediaBean;
import com.yang.keyboard.media.MediaListener;
import com.yang.keyboard.utils.OnKeyBoardLister;

/**
 * Created by yangc on 2017/3/7.
 * E-Mail:1007181167@qq.com
 * Description：键盘封装展示  方便集成
 */

public class KeyboardFragment extends Fragment implements MediaListener, ChatKeyboardLayout.OnChatKeyBoardListener {

    private static final String TAG = "KeyboardFragment";
    ChatKeyboardLayout keyboardLayout = null;
    RecordingLayout rlRecordArea;
    OnKeyBoardLister keyBoardLister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.simple_fragment_keybord_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keyboardLayout = (ChatKeyboardLayout) view.findViewById(R.id.kv_bar);
        rlRecordArea = (RecordingLayout) view.findViewById(R.id.recording_area);
        keyboardLayout.showEmoticons();
        ArrayList<MediaBean> popupModels = intiData();
        keyboardLayout.showMedias(popupModels);
        keyboardLayout.setOnKeyBoardBarListener(this);
    }

    /****
     * 重写实现选择器内容选择器
     * ******/
    protected ArrayList<MediaBean> intiData() {
        ArrayList<MediaBean> popupModels = new ArrayList<>();
        popupModels.add(new MediaBean(0, R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(1, R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(2, R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(3, R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(4, R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(5, R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(6, R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(7, R.drawable.icon_photo, "照片", this));
        popupModels.add(new MediaBean(8, R.drawable.icon_photo, "拍照", this));
        popupModels.add(new MediaBean(9, R.drawable.icon_photo, "照片", this));
        return popupModels;
    }

    @Override
    public void onMediaClick(int id) {
        if (keyBoardLister != null) {
            keyBoardLister.sendMedia(id);
        }
    }

    @Override
    public void onSendBtnClick(String msg) {
        if (keyBoardLister != null) {
            keyBoardLister.sendText(msg);
        }
    }

    @Override
    public void onRecordingAction(ChatKeyboardLayout.RecordingAction action) {
        switch (action) {
            case START:
                String mVoicePath = AudioManger.getInstance().generatePath(getActivity());
                AudioManger.getInstance().start(mVoicePath, new AudioListener());
                rlRecordArea.show(1);
                break;
            case RESTORE:
                rlRecordArea.show(1);
                break;
            case WILLCANCEL:
                rlRecordArea.show(0);
                break;
            case CANCELED:
                AudioManger.getInstance().cancel();
                rlRecordArea.hide();
                break;
            case COMPLETE:
                int time = AudioManger.getInstance().complete();
                if (time < 0) {
                    Toast.makeText(getActivity(), "time is too short", Toast.LENGTH_SHORT).show();
                } else {
                    if (keyBoardLister != null) {
                        keyBoardLister.sendAudio(AudioManger.getInstance().getmPath(), time);
                    }
                }
                Log.d(TAG, "time:" + time + "mVoicePath" + AudioManger.getInstance().getmPath());
                rlRecordArea.hide();
                break;
        }
    }

    @Override
    public void onUserDefEmoticonClicked(String tag, String uri) {
        if (keyBoardLister != null) {
            keyBoardLister.sendUserDefEmoticon(tag, uri);
        }
    }

    /***
     * 语音音量的大小监听
     * ***/
    private class AudioListener implements AudioManger.OnAudioListener {
        @Override
        public void onDbChange(double db) {
            int level = 0;
            Log.e("pengtao", "onDbChange db = " + db);
            if (db > 40) {
                level = ((int) db - 40) / 7;
            }
            Log.e("pengtao", "onDbChange level = " + level);
            rlRecordArea.setVoiceLevel(level);
        }
    }

    public void setKeyBoardLister(OnKeyBoardLister keyBoardLister) {
        this.keyBoardLister = keyBoardLister;
    }
}
