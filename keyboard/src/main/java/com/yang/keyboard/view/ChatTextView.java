package com.yang.keyboard.view;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.yang.keyboard.emoticon.util.EmoticonHandler;
import com.yang.keyboard.utils.Utils;


/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[展示自定义表情TextView]
 **/
public class ChatTextView extends TextView{
    Context mContext;

    public ChatTextView(Context context) {
        super(context);
        init(context);
    }

    public ChatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!TextUtils.isEmpty(text)) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            EmoticonHandler.getInstance(mContext).setTextFace(text.toString(), builder, 0, Utils.getFontSize(getTextSize()) );
            text = builder;
        }
        super.setText(text, type);
    }
}
