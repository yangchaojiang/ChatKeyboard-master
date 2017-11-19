package com.yang.keyboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yang.keyboard.ChatKeyboardLayout;
import com.yang.keyboard.R;
import com.yang.keyboard.utils.Utils;

/**
 * Created by yangc on 2017/3/7.
 * E-Mail:1007181167@qq.com
 * Description： 自定义音语音a按钮
 */

public class AudioRecordButton extends android.support.v7.widget.AppCompatButton {


    public AudioRecordButton(Context context) {
        super(context);
        initView();
    }

    public AudioRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AudioRecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private OnRecordingTouchListener onRecordingTouchListener;
    /****
     * 初始化
     * ****/
    private void initView() {
        setOnTouchListener(new RecordingTouchListener());
    }


    /****
     * 动作操作
     * **/
    private class RecordingTouchListener implements OnTouchListener {
        float startY;
        float endY;
        boolean isCanceled = false;
        private long currentTimeMillis;//记录按下时间

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (currentTimeMillis != 0 && System.currentTimeMillis() - currentTimeMillis < 1000) {
                    if (onRecordingTouchListener != null) {
                        onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.CANCELED);
                    }
                    setText(getResources().getString(R.string.recording_start));
                    setBackgroundResource(R.drawable.recording_n);
                    return false;
                }
                currentTimeMillis = System.currentTimeMillis();
                startY = motionEvent.getRawY();
                setText(getResources().getString(R.string.recording_end));
                setBackgroundResource(R.drawable.recording_p);
                if (onRecordingTouchListener != null) {
                    onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.START);
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setText(getResources().getString(R.string.recording_start));
                setBackgroundResource(R.drawable.recording_n);
                if (onRecordingTouchListener != null && !isCanceled) {
                    onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.COMPLETE);
                } else if (onRecordingTouchListener != null) {
                    onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.CANCELED);
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                //todo the num can be set by up layer
                endY = motionEvent.getRawY();
                if (startY - endY > Utils.dip2px(getContext(), 50)) {
                    setText(getResources().getString(R.string.recording_cancel));
                    isCanceled = true;
                    if (onRecordingTouchListener != null) {
                        onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.WILLCANCEL);
                    }
                } else {
                    if (onRecordingTouchListener != null) {
                        onRecordingTouchListener.onRecordingAction(AudioRecordButton.this,ChatKeyboardLayout.RecordingAction.RESTORE);
                    }
                    setText(getResources().getString(R.string.recording_end));
                    isCanceled = false;
                }
            }
            return false;
        }
    }


    /****
     * 动作回调借口
     * **/
    public interface OnRecordingTouchListener {
        void onRecordingAction(AudioRecordButton button,ChatKeyboardLayout.RecordingAction action);
    }

    public void setOnRecordingTouchListener(OnRecordingTouchListener onRecordingTouchListener) {
        this.onRecordingTouchListener = onRecordingTouchListener;
    }

}
