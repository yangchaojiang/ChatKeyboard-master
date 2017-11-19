package com.yang.keyboard.media;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import java.util.List;

import com.yang.keyboard.R;
import com.yang.keyboard.view.IndicatorView;


/**
 * Created by yangjiang on 2017/04/07.
 * E-Mail:1007181167@qq.com
 * Description:[自定义选择的布局]
 **/
public class MediaLayout extends RelativeLayout implements ViewPager.OnPageChangeListener {
    ViewPager vpContent;
    IndicatorView ivIndicator;
    Context mContext;

    public MediaLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MediaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MediaLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.keyboard_bottom_media, this);
        vpContent = (ViewPager) findViewById(R.id.popup_media_pager);
        ivIndicator = (IndicatorView) findViewById(R.id.popup_media_indicator);
        vpContent.addOnPageChangeListener(this);  //compatible for android 22
    }

    public void setContents(List<MediaBean> mediaContents) {
        int size = getResources().getDimensionPixelSize(R.dimen.media_item_size);
        MediaPagerAdapter adapter = new MediaPagerAdapter(mContext, mediaContents, size);
        vpContent.setAdapter(adapter);
        ivIndicator.setIndicatorCount(adapter.getPageNum());
        if (adapter.getPageNum()<2){
            ivIndicator.setVisibility(GONE);
        }else{
            ivIndicator.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ivIndicator.moveTo(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
