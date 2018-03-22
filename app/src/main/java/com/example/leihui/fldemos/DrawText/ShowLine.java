package com.example.leihui.fldemos.DrawText;

import java.util.List;

/**
 * Created by leihui on 2018/3/8.
 * 可见行数据
 */

public class ShowLine {

    public List<ShowChar> mCharsData = null;

    public String getLineData() {
        String lineData = "";
        if (mCharsData == null || mCharsData.size() == 0) {
            return lineData;
        }
        for (ShowChar c : mCharsData) {
            lineData = lineData + c.mCharData;
        }
        return lineData;
    }

    @Override
    public String toString() {
        return "ShowLine [Linedata=" + getLineData() + "]";
    }
}
