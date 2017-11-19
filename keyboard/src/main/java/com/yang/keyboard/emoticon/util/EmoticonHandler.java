package com.yang.keyboard.emoticon.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Spannable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yang.keyboard.emoticon.bean.EmoticonEntity;
import com.yang.keyboard.emoticon.EmoticonSetBean;
import com.yang.keyboard.emoticon.db.EmoticonDBHelper;
import com.yang.keyboard.emoticon.EmoticonBean;
import com.yang.keyboard.utils.EmoticonBase;
import com.yang.keyboard.utils.EmoticonLoader;
import com.yang.keyboard.utils.HadLog;
import com.yang.keyboard.utils.Utils;
import com.yang.keyboard.view.VerticalImageSpan;

import org.xmlpull.v1.XmlPullParserException;

/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[表情数据库操作帮助类]
 **/
public class EmoticonHandler {
    private static ArrayList<EmoticonBean> mEmoticonBeans = new ArrayList<>();
    private static EmoticonHandler sEmoticonHandler = null;
    private Context mContext;
    private EmoticonDBHelper emoticonDbHelper = null;

    public void release() {
        mEmoticonBeans.clear();
        if (emoticonDbHelper != null) {
            emoticonDbHelper.cleanup();
        }
        sEmoticonHandler=null;
    }

    public static EmoticonHandler getInstance(@NonNull Context context) {
        if (sEmoticonHandler == null) {
            sEmoticonHandler = new EmoticonHandler(context);
        }
        return sEmoticonHandler;
    }

    private EmoticonHandler(Context context) {
        mContext = context;
        emoticonDbHelper = new EmoticonDBHelper(context);
    }

    public EmoticonDBHelper getEmoticonDbHelper() {
        if (emoticonDbHelper == null) {
            emoticonDbHelper = new EmoticonDBHelper(mContext);
        }
        return emoticonDbHelper;
    }

    public ArrayList<EmoticonBean> loadEmoticonsToMemory() {
        mEmoticonBeans = emoticonDbHelper.queryAllEmoticonBeans();
        emoticonDbHelper.cleanup();

        return mEmoticonBeans;
    }

    public String getEmoticonUriByTag(String tag) {
        return emoticonDbHelper.getUriByTag(tag);
    }

    public void setTextFace(String content, Spannable spannable, int start, int size) {
        if (mEmoticonBeans == null) {
            mEmoticonBeans = emoticonDbHelper.queryAllEmoticonBeans();
            emoticonDbHelper.cleanup();
        }
        if (content.length() <= 0) {
            return;
        }
        int keyIndex = start;
        for (EmoticonBean bean : mEmoticonBeans) {
            String key = bean.getTag();
            int keyLength = key.length();
            while (keyIndex >= 0) {
                keyIndex = content.indexOf(key, keyIndex);  //when do not find, get -1
                if (keyIndex < 0) {
                    break;
                }
                Drawable drawable = EmoticonLoader.getInstance(mContext).getDrawable(bean.getIconUri());
                drawable.setBounds(0, 0, size, size);
                VerticalImageSpan imageSpan = new VerticalImageSpan(drawable);
                spannable.setSpan(imageSpan, keyIndex, keyIndex + keyLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                keyIndex += keyLength;
            }
            keyIndex = start;
        }
    }
    public static boolean isEmoticonInitSuccess(Context context) {
        return Utils.isInitDb(context);
    }
    public static void initEmoticonsDB(final Context context, final boolean isShowEmoji, final List<EmoticonEntity> emoticonEntities) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EmoticonDBHelper emoticonDbHelper = EmoticonHandler.getInstance(context).getEmoticonDbHelper();
                if (isShowEmoji) {
                    ArrayList<EmoticonBean> emojiArray = Utils.ParseData(DefEmoticons.emojiArray, EmoticonBean.FACE_TYPE_NORMAL, EmoticonBase.Scheme.DRAWABLE);
                    EmoticonSetBean emojiEmoticonSetBean = new EmoticonSetBean("emoji", 3, 7);
                    emojiEmoticonSetBean.setIconUri("drawable://icon_emoji");
                    emojiEmoticonSetBean.setItemPadding(25);
                    emojiEmoticonSetBean.setVerticalSpacing(10);
                    emojiEmoticonSetBean.setShowDelBtn(true);
                    emojiEmoticonSetBean.setEmoticonList(emojiArray);
                    emoticonDbHelper.insertEmoticonSet(emojiEmoticonSetBean);
                }

                List<EmoticonSetBean> emoticonSetBeans = new ArrayList<>();
                for (EmoticonEntity entity : emoticonEntities) {
                    try {
                        EmoticonSetBean bean = Utils.ParseEmoticons(context, entity.getPath(), entity.getScheme());
                        emoticonSetBeans.add(bean);
                    } catch (IOException e) {
                        e.printStackTrace();
                        HadLog.e(String.format("read %s config.xml error", entity.getPath()));
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                        HadLog.e(String.format("parse %s config.xml error", entity.getPath()));
                    }
                }

                for (EmoticonSetBean setBean : emoticonSetBeans) {
                    emoticonDbHelper.insertEmoticonSet(setBean);
                }
                emoticonDbHelper.cleanup();

                if (emoticonSetBeans.size() == emoticonEntities.size()) {
                    Utils.setIsInitDb(context, true);
                }
            }
        }).start();
    }
}
