package com.yang.keyboard_example;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.yang.keyboard.utils.OnKeyBoardLister;
import com.yang.keyboard_example.models.DefaultUser;
import com.yang.keyboard_example.models.MyMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;

public class MainActivity extends AppCompatActivity implements OnKeyBoardLister {
    UserFragment userFragment;
    MessageList messageList;
    MsgListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        userFragment = (UserFragment) getSupportFragmentManager().findFragmentById(R.id.text);
        userFragment.setKeyBoardLister(this);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFragment.getChatKeyboardLayout().hideKeyboard();
            }
        });
        messageList = (MessageList) findViewById(R.id.msg_list);
        userFragment.getView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userFragment.getChatKeyboardLayout().hideKeyboard();
                return false;
            }
        });
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                if (string.contains("R.drawable")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.drawable.", ""),
                            "drawable", getPackageName());

                    avatarImageView.setImageResource(resId);
                } else {
                    Glide.with(getApplicationContext())
                            .load(string)
                            .placeholder(R.drawable.aurora_headicon_default)
                            .into(avatarImageView);
                }
            }

            @Override
            public void loadImage(ImageView imageView, String string) {
                Glide.with(getApplicationContext())
                        .load(string)
                        .fitCenter()
                        .placeholder(R.drawable.aurora_picture_not_found)
                        .override(400, Target.SIZE_ORIGINAL)
                        .into(imageView);
            }
        };

        adapter = new MsgListAdapter<MyMessage>("1", imageLoader);
        adapter.addToEnd(getMessages());
        messageList.setAdapter(adapter);
        messageList.postDelayed(new Runnable() {
            @Override
            public void run() {
                messageList.scrollToPosition(0);
            }
        }, 300);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void sendText(String text) {
        MyMessage message = new MyMessage(text, IMessage.MessageType.SEND_TEXT);
        message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
        message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        adapter.addToStart(message, true);
    }

    @Override
    public void sendUserDefEmoticon(String tag, String uri) {

    }

    @Override
    public void sendAudio(String path, int time) {
        MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE);
        message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
        message.setMediaFilePath(path);
        message.setDuration(time);
        message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
        adapter.addToStart(message, true);
    }

    @Override
    public void sendMedia(int ids) {

    }

    @Override
    public void clickAddBtn(View view) {
        messageList.scrollToPosition(0);

    }

    private List<MyMessage> getMessages() {
        List<MyMessage> list = new ArrayList<>();
        Resources res = getResources();
        String[] messages = res.getStringArray(R.array.messages_array);
        for (int i = 0; i < messages.length; i++) {
            MyMessage message;
            if (i % 2 == 0) {
                message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT);
                message.setUserInfo(new DefaultUser("0", "DeadPool", "R.drawable.deadpool"));
            } else {
                message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT);
                message.setUserInfo(new DefaultUser("1", "IronMan", "R.drawable.ironman"));
            }
            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            list.add(message);
        }
        Collections.reverse(list);
        return list;
    }
}
