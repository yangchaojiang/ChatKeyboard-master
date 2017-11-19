package com.yang.keyboard.emoticon.bean;

import com.yang.keyboard.emoticon.EmoticonBean;
/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[每页的表情item 点击接口]
 **/
public interface IView {
    void onItemClick(EmoticonBean bean);
    void onPageChangeTo(int position);
}
