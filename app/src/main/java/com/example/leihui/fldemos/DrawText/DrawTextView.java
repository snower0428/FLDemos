package com.example.leihui.fldemos.DrawText;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leihui on 2018/3/8.
 *
 */

public class DrawTextView extends View {

    private static final String TAG = "DrawTextView";
    private List<String> mTextList = new ArrayList<>();
    private List<ShowLine> mTextData = new ArrayList<>();
    private List<ShowLine> mSelectLines = new ArrayList<>();

    private Paint mPaint = null;
    private Paint mTextSelectPaint = null;
    private Paint mBorderPointPaint = null;
    private int mTextSelectColor = Color.parseColor("#77fadb08");
    private int mBorderPointColor = Color.RED;

    private float mFontSize = 60;
    private float mTextHeight = 0;
    private float mLineYPosition = 0;
    private int mLinePadding = 30;
    private DrawMode mCurrentMode = DrawMode.DRAW_MODE_NORMAL;
    private float mDownX = -1;
    private float mDownY = -1;
    private float mTouchX = 0;
    private float mTouchY = 0;

    private Path mSelectTextPath = new Path();
    private ShowChar mFirstSelectShowChar = null;
    private ShowChar mLastSelectShowChar = null;

    private float mBorderPointRadius = 30;
    private int mMarginWidth = 60;
    private int mMarginHeight = 90;

    public enum DrawMode {
        DRAW_MODE_NORMAL,       // 正常模式
        DRAW_MODE_SELECT_TEXT,  // 长按选中文字
        DRAW_MODE_MOVE_FORWARD, // 向前滑动选中文字
        DRAW_MODE_MOVE_BACK,    // 向后滑动选中文字
    }

    private OnLongClickListener mLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mCurrentMode == DrawMode.DRAW_MODE_NORMAL) {
                if (mDownX > 0 && mDownY > 0) { // 说明还没释放，是长按事件
                    mCurrentMode = DrawMode.DRAW_MODE_SELECT_TEXT;
                    postInvalidate();
                }
            }
            return false;
        }
    };

    public DrawTextView(Context context) {
        super(context);
        initData();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mFontSize);

        mTextSelectPaint = new Paint();
        mTextSelectPaint.setAntiAlias(true);
        mTextSelectPaint.setTextSize(mFontSize);
        mTextSelectPaint.setColor(mTextSelectColor);

        mBorderPointPaint = new Paint();
        mBorderPointPaint.setAntiAlias(true);
        mBorderPointPaint.setTextSize(mFontSize);
        mBorderPointPaint.setColor(mBorderPointColor);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = Math.abs(fontMetrics.ascent) + Math.abs(fontMetrics.descent);

        setOnLongClickListener(mLongClickListener);

        mTextList.add("  第一章");
        mTextList.add("  （1）");
        mTextList.add("  1977年10月20日星期六，俄亥俄");
        mTextList.add("州立大学校警警长柯约翰，派出许多");
        mTextList.add("警力守护整座医学院，警车和徒步警");
        mTextList.add("员到处可见，建筑楼顶上也都有荷枪");
        mTextList.add("实弹的武装警察监视。妇女们已接获");
        mTextList.add("警告不可单独外出，尤其是在进入车");
        mTextList.add("内时，更要留意附近是否有任何可疑");
        mTextList.add("男子逗留。\n");
        mTextList.add("  八天之内，已发生第二宗校园年");
        mTextList.add("轻女子在槍口威胁下遭绑架的案件，");
        mTextList.add("时间都是在早晨八点至九点之间。第");
        mTextList.add("一位受害者是25岁的眼科学生，第二");
        mTextList.add("位是24岁的护士，她们都被载到荒郊");
        mTextList.add("野地先遭强暴，继而被迫去银行兑现");
        mTextList.add("支票洗劫一空。\n");
        mTextList.add("  报纸上刊出嫌疑犯的素描画像，");
        mTextList.add("数以百计的电话打到警察局，通报了");

        int index = 0;
        for (int i = 0; i < mTextList.size(); i++) {
            String strLine = mTextList.get(i);
            char[] charArray = strLine.toCharArray();

            List<ShowChar> charList = new ArrayList<>();
            for (int c = 0; c < charArray.length; c++) {
                String measureStr = String.valueOf(charArray[c]);
                float charWidth = mPaint.measureText(measureStr);

                ShowChar showChar = new ShowChar();
                showChar.mCharData = charArray[c];
                showChar.mCharWidth = charWidth;
                showChar.mIndex = index++;
                charList.add(showChar);
            }
            ShowLine showLine = new ShowLine();
            showLine.mCharsData = charList;
            mTextData.add(showLine);
        }
        Log.d(TAG, "" + mTextData);
    }

    // 检测是否准备滑动选择文字
    private boolean checkIfTrySelectMove(float xPosition, float yPosition) {
        if (mFirstSelectShowChar == null || mLastSelectShowChar == null) {
            return false;
        }

        float flx, fty, frx, fby;

        float padding = mFirstSelectShowChar.mCharWidth;
        padding = padding < 30 ? 30 : padding;

        flx = mFirstSelectShowChar.mTopLeftPosition.x - padding;
        frx = mFirstSelectShowChar.mTopLeftPosition.x;

        fty = mFirstSelectShowChar.mTopLeftPosition.y;
        fby = mFirstSelectShowChar.mBottomLeftPosition.y;

        float llx, lty, lrx, lby;

        llx = mLastSelectShowChar.mBottomRightPosition.x;
        lrx = mLastSelectShowChar.mBottomRightPosition.x + padding;

        lty = mLastSelectShowChar.mTopRightPosition.y;
        lby = mLastSelectShowChar.mBottomRightPosition.y;

        if ((xPosition >= flx && xPosition <= frx) && (yPosition >= fty && yPosition <= fby)) {
            mCurrentMode = DrawMode.DRAW_MODE_MOVE_FORWARD;
            return true;
        }

        if ((xPosition >= llx && xPosition <= lrx) && (yPosition >= lty && yPosition <= lby)) {
            mCurrentMode = DrawMode.DRAW_MODE_MOVE_BACK;
            return true;
        }

        return false;
    }

    private boolean canMoveForward(float touchX, float touchY) {
        Path p = new Path();
        p.moveTo(mLastSelectShowChar.mTopRightPosition.x, mLastSelectShowChar.mTopRightPosition.y);
        p.lineTo(getWidth(), mLastSelectShowChar.mTopRightPosition.y);
        p.lineTo(getWidth(), 0);
        p.lineTo(0, 0);
        p.lineTo(0, mLastSelectShowChar.mBottomRightPosition.y);
        p.lineTo(mLastSelectShowChar.mBottomRightPosition.x, mLastSelectShowChar.mBottomRightPosition.y);
        p.lineTo(mLastSelectShowChar.mTopRightPosition.x, mLastSelectShowChar.mTopRightPosition.y);

        return computeRegion(p).contains((int) touchX, (int) touchY);
    }

    private boolean canMoveBack(float touchX, float touchY) {
        Path p = new Path();
        p.moveTo(mFirstSelectShowChar.mTopLeftPosition.x, mFirstSelectShowChar.mTopRightPosition.y);
        p.lineTo(getWidth(), mFirstSelectShowChar.mTopLeftPosition.y);
        p.lineTo(getWidth(), getHeight());
        p.lineTo(0, getHeight());
        p.lineTo(0, mFirstSelectShowChar.mBottomLeftPosition.y);
        p.lineTo(mFirstSelectShowChar.mBottomLeftPosition.x, mFirstSelectShowChar.mBottomLeftPosition.y);
        p.lineTo(mFirstSelectShowChar.mTopLeftPosition.x, mFirstSelectShowChar.mTopLeftPosition.y);

        return computeRegion(p).contains((int) touchX, (int) touchY);
    }

    // 通过路径计算区域
    private Region computeRegion(Path path) {
        Region region = new Region();
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return region;
    }

    // 检测获取按压坐标所在位置的字符，没有的话返回null
    private ShowChar detectPressShowChar(float touchX, float touchY) {
        for (ShowLine l : mTextData) {
            for (ShowChar c : l.mCharsData) {
                if (touchY > c.mBottomLeftPosition.y) {
                    break;  // 说明是在下一行
                }
                if (touchX >= c.mBottomLeftPosition.x && touchX <= c.mBottomRightPosition.x) {
                    return c;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mTouchX;
                mDownY = mTouchY;
                if (mCurrentMode != DrawMode.DRAW_MODE_NORMAL) {
                    boolean isTrySelectMove = checkIfTrySelectMove(mDownX, mDownY);
                    if (!isTrySelectMove) {
                        mCurrentMode = DrawMode.DRAW_MODE_NORMAL;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentMode == DrawMode.DRAW_MODE_MOVE_FORWARD) {
                    if (canMoveForward(event.getX(), event.getY())) {
                        Log.d(TAG, "canMoveForward");
                        ShowChar firstSelectChar = detectPressShowChar(event.getX(), event.getY());
                        if (firstSelectChar != null) {
                            mFirstSelectShowChar = firstSelectChar;
                            invalidate();
                        }
                        else {
                            Log.d(TAG, "firstSelectChar is null");
                        }
                    }
                    else {
                        Log.d(TAG, "not canMoveForward");
                    }
                }
                else if (mCurrentMode == DrawMode.DRAW_MODE_MOVE_BACK) {
                    if (canMoveBack(event.getX(), event.getY())) {
                        Log.d(TAG, "canMoveBack");
                        ShowChar lastSelectChar = detectPressShowChar(event.getX(), event.getY());
                        if (lastSelectChar != null) {
                            mLastSelectShowChar = lastSelectChar;
                            invalidate();
                        }
                        else {
                            Log.d(TAG, "lastSelectChar is null");
                        }
                    }
                    else {
                        Log.d(TAG, "not canMoveBack");
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mDownX = -1;
                mDownY = -1;
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mLineYPosition = mTextHeight + mLinePadding;

        for (ShowLine line : mTextData) {
            drawLineText(line, canvas);
        }

        if (mCurrentMode != DrawMode.DRAW_MODE_NORMAL) {
            drawSelectText(canvas);
        }
    }

    private void drawSelectText(Canvas canvas) {
        if (mCurrentMode == DrawMode.DRAW_MODE_SELECT_TEXT) {
            drawPressSelectText(canvas);
        }
        else if (mCurrentMode == DrawMode.DRAW_MODE_MOVE_FORWARD) {
            drawMoveSelectText(canvas);
        }
        else if (mCurrentMode == DrawMode.DRAW_MODE_MOVE_BACK) {
            drawMoveSelectText(canvas);
        }
    }

    private void drawMoveSelectText(Canvas canvas) {
        if (mFirstSelectShowChar == null || mLastSelectShowChar == null) {
            return;
        }
        getSelectData();
        drawSelectLines(canvas);
        drawBorderPoint(canvas);
    }

    private void drawLineText(ShowLine line, Canvas canvas) {
        canvas.drawText(line.getLineData(), mMarginWidth, mMarginHeight + mLineYPosition, mPaint);

        float leftPosition = 0;
        float rightPosition = 0;
        float bottomPosition = mLineYPosition + mPaint.getFontMetrics().descent;

        for (ShowChar c : line.mCharsData) {
            rightPosition = leftPosition + c.mCharWidth;
            Point tlp = new Point();
            c.mTopLeftPosition = tlp;
            tlp.x = (int) leftPosition;
            tlp.y = (int) (bottomPosition - mTextHeight);

            Point blp = new Point();
            c.mBottomLeftPosition = blp;
            blp.x = (int) leftPosition;
            blp.y = (int) bottomPosition;

            Point trp = new Point();
            c.mTopRightPosition = trp;
            trp.x = (int) rightPosition;
            trp.y = (int) (bottomPosition - mTextHeight);

            Point brp = new Point();
            c.mBottomRightPosition = brp;
            brp.x = (int) rightPosition;
            brp.y = (int) bottomPosition;

            leftPosition = rightPosition;
        }
        mLineYPosition = mLineYPosition + mTextHeight + mLinePadding;
    }

    private void getSelectData() {
        boolean started = false;
        boolean ended = false;
        mSelectLines.clear();

        // 找到选择的字符数据，转化为选择的行，然后将行选择背景画出来
        for (ShowLine l : mTextData) {
            ShowLine selectLine = new ShowLine();
            selectLine.mCharsData = new ArrayList<>();

            for (ShowChar c : l.mCharsData) {
                if (!started) {
                    if (c.mIndex == mFirstSelectShowChar.mIndex) {
                        started = true;
                        selectLine.mCharsData.add(c);
                        if (c.mIndex == mLastSelectShowChar.mIndex) {
                            ended = true;
                            break;
                        }
                    }
                }
                else {
                    if (c.mIndex == mLastSelectShowChar.mIndex) {
                        ended = true;
                        if (!selectLine.mCharsData.contains(c)) {
                            selectLine.mCharsData.add(c);
                        }
                        break;
                    }
                    else {
                        selectLine.mCharsData.add(c);
                    }
                }
            }
            mSelectLines.add(selectLine);
            if (started && ended) {
                break;
            }
        }
        Log.d(TAG, "mSelectLines:" + mSelectLines);
    }

    private void drawSelectLines(Canvas canvas) {
        drawRectangleSelectLinesBg(canvas);
    }

    private void drawRectangleSelectLinesBg(Canvas canvas) {
        for (ShowLine l : mSelectLines) {
            if (l.mCharsData != null && l.mCharsData.size() > 0) {
                mSelectTextPath.reset();

                ShowChar firstChar = l.mCharsData.get(0);
                ShowChar lastChar = l.mCharsData.get(l.mCharsData.size() - 1);

                mSelectTextPath.moveTo(firstChar.mTopLeftPosition.x, firstChar.mTopLeftPosition.y);
                mSelectTextPath.lineTo(lastChar.mTopRightPosition.x, lastChar.mTopRightPosition.y);
                mSelectTextPath.lineTo(lastChar.mBottomRightPosition.x, lastChar.mBottomRightPosition.y);
                mSelectTextPath.lineTo(firstChar.mBottomLeftPosition.x, firstChar.mBottomLeftPosition.y);
                mSelectTextPath.lineTo(firstChar.mTopLeftPosition.x, firstChar.mTopLeftPosition.y);

                canvas.drawPath(mSelectTextPath, mTextSelectPaint);
            }
        }
    }

    private void drawPressSelectText(Canvas canvas) {
        ShowChar p = detectPressShowChar(mDownX, mDownY);
        if (p != null) {    // 找到选择的字符
            mFirstSelectShowChar = mLastSelectShowChar = p;
            mSelectTextPath.reset();
            mSelectTextPath.moveTo(p.mTopLeftPosition.x, p.mTopLeftPosition.y);
            mSelectTextPath.lineTo(p.mTopRightPosition.x, p.mTopRightPosition.y);
            mSelectTextPath.lineTo(p.mBottomRightPosition.x, p.mBottomRightPosition.y);
            mSelectTextPath.lineTo(p.mBottomLeftPosition.x, p.mBottomLeftPosition.y);
            canvas.drawPath(mSelectTextPath, mTextSelectPaint);

            drawBorderPoint(canvas);
        }
    }

    private void drawBorderPoint(Canvas canvas) {
        if (mFirstSelectShowChar != null && mLastSelectShowChar != null) {
            drawPoint(canvas);
        }
    }

    private void drawPoint(Canvas canvas) {
        float padding = 0;

        canvas.drawCircle(mFirstSelectShowChar.mTopLeftPosition.x - padding,
                mFirstSelectShowChar.mTopLeftPosition.y - padding, mBorderPointRadius, mBorderPointPaint);
        canvas.drawCircle(mLastSelectShowChar.mBottomRightPosition.x + padding,
                mLastSelectShowChar.mBottomRightPosition.y, mBorderPointRadius, mBorderPointPaint);
        canvas.drawLine(mFirstSelectShowChar.mTopLeftPosition.x - padding, mFirstSelectShowChar.mTopLeftPosition.y - padding,
                mFirstSelectShowChar.mBottomLeftPosition.x - padding, mFirstSelectShowChar.mBottomLeftPosition.y, mBorderPointPaint);
        canvas.drawLine(mLastSelectShowChar.mBottomRightPosition.x + padding, mLastSelectShowChar.mBottomRightPosition.y + padding,
                mLastSelectShowChar.mTopRightPosition.x + padding, mLastSelectShowChar.mTopRightPosition.y, mBorderPointPaint);
    }
}
