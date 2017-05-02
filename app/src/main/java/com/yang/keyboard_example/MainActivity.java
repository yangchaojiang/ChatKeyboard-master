package com.yang.keyboard_example;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yang.keyboard.ChatKeyboardLayout;
import com.yang.keyboard.KeyboardFragment;
import com.yang.keyboard.RecordingLayout;
import com.yang.keyboard.audio.AudioManger;
import com.yang.keyboard.utils.OnKeyBoardLister;

public class MainActivity extends AppCompatActivity implements OnKeyBoardLister {
    SimpleChatAdapter mAdapter;
    UserFragment keyboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FragmentTransaction sss = getSupportFragmentManager().beginTransaction();
        keyboardFragment = new UserFragment();
        sss.add(R.id.sssssssss, keyboardFragment);
        sss.commit();
        keyboardFragment.setKeyBoardLister(this);

    }

    @Override
    public void sendText(String text) {
        mAdapter.addItem(new ChatBean(null, text));
    }

    @Override
    public void sendUserDefEmoticon(String tag, String uri) {
        mAdapter.addItem(new ChatBean(tag, null));
    }

    @Override
    public void sendAudio(String path, int time) {

    }

    @Override
    public void sendMedia(int ids) {

    }

    @Override
    public void clickAddBtn(View view) {

    }


}
