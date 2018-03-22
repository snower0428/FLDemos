package com.example.leihui.fldemos.DrawText;

import android.graphics.Point;

/**
 * Created by leihui on 2018/3/8.
 * 可见字符数据
 */

public class ShowChar {

    // 字符数据
    public char mCharData;

    // 当前字符是否被选中
    public boolean mSelected = false;

    // 字符左上、右上、左下、右下位置
    public Point mTopLeftPosition = null;
    public Point mTopRightPosition = null;
    public Point mBottomLeftPosition = null;
    public Point mBottomRightPosition = null;

    // 字符宽度
    public float mCharWidth = 0;

    // 当前字符位置
    public int mIndex = 0;

    @Override
    public String toString() {
        return "ShowChar [chardata=" + mCharData + ", Selected=" + mSelected + ", TopLeftPosition=" + mTopLeftPosition
                + ", TopRightPosition=" + mTopRightPosition + ", BottomLeftPosition=" + mBottomLeftPosition
                + ", BottomRightPosition=" + mBottomRightPosition + ", charWidth=" + mCharWidth + ", Index=" + mIndex
                + "]";
    }
}
