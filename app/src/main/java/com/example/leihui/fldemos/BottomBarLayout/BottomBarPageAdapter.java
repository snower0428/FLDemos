package com.example.leihui.fldemos.BottomBarLayout;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by leihui on 2018/2/23.
 *
 */

public class BottomBarPageAdapter extends PagerAdapter {

    private ArrayList<View> mViewList;

    public BottomBarPageAdapter(ArrayList<View> viewList) {
        super();
        this.mViewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
