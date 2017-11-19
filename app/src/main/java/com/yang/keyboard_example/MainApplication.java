package com.yang.keyboard_example;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import com.yang.keyboard.emoticon.bean.EmoticonEntity;
import com.yang.keyboard.emoticon.util.EmoticonHandler;
import com.yang.keyboard.utils.EmoticonBase;

/**
 * MainApplication
 * Created by 90Chris on 2015/10/8.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if ( !EmoticonHandler.isEmoticonInitSuccess(this) ) {
            List<EmoticonEntity> entities = new ArrayList<>();
            entities.add(new EmoticonEntity("emoticons/xhs", EmoticonBase.Scheme.ASSETS));
            entities.add(new EmoticonEntity("emoticons/tusiji", EmoticonBase.Scheme.ASSETS));
            EmoticonHandler.initEmoticonsDB(this, false, entities);
        }
    }
}