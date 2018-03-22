package com.example.leihui.fldemos.DrawText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leihui on 2018/3/8.
 * BreakResult
 */

public class BreakResult {

    public int mCharNums = 0;
    public boolean mIsFullLine = false;
    public List<ShowChar> mShowChars = null;

    public BreakResult() {
        mShowChars = new ArrayList<>();
    }

    public boolean hasData() {
        return (mShowChars != null && mShowChars.size() > 0);
    }
}
