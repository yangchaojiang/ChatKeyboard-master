package com.yang.keyboard.emoticon.util;


import java.util.ArrayList;

import com.yang.keyboard.emoticon.EmoticonSetBean;
/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[表情点击初始化]
 **/
public class EmoticonsKeyboardBuilder {

    public Builder builder;

    public EmoticonsKeyboardBuilder(Builder builder){
        this.builder = builder;
    }

    public static class Builder {

        ArrayList<EmoticonSetBean> mEmoticonSetBeanList = new ArrayList<EmoticonSetBean>();

        public Builder(){ }

        public ArrayList<EmoticonSetBean> getEmoticonSetBeanList() { return mEmoticonSetBeanList; }

        public Builder setEmoticonSetBeanList(ArrayList<EmoticonSetBean> mEmoticonSetBeanList) { this.mEmoticonSetBeanList = mEmoticonSetBeanList;  return this;}

        public EmoticonsKeyboardBuilder build() { return new EmoticonsKeyboardBuilder(this); }
    }
}
