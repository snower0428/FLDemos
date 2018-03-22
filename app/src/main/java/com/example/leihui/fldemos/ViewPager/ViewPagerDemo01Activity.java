package com.example.leihui.fldemos.ViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.leihui.fldemos.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerDemo01Activity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private ViewPager mViewPager;
    private List<Fragment> mList;
    private FragAdapter mAdapter;

    private boolean mMisScrolled = false;

    private List<TextView> mTextViewList;
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo01);

        initViews();
    }

    private void initViews() {
        // Back
        ImageView imageViewBack = findViewById(R.id.image_view_back);
        if (imageViewBack != null) {
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        mTextViewList = new ArrayList<>();
        mTextViewList.add((TextView) findViewById(R.id.text_view_01));
        mTextViewList.add((TextView) findViewById(R.id.text_view_02));
        mTextViewList.add((TextView) findViewById(R.id.text_view_03));

        mViewPager = findViewById(R.id.view_pager);
        mList = new ArrayList<>();
        mList.add(new Frag1());
        mList.add(new Frag2());
        mList.add(new Frag3());
        mAdapter = new FragAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * @param position 当前页面
             * @param positionOffset 当前页面偏移的百分比
             * @param positionOffsetPixels 当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d(TAG, "onPageScrolled ----------> position:" + position +
//                        "\nonPageScrolled ----------> positionOffset:" + positionOffset +
//                        "\nonPageScrolled ----------> positionOffsetPixels:" + positionOffsetPixels);
            }

            /**
             * 此方法是页面跳转完后被调用
             * @param position 当前选中的页面的Position
             */
            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected ----------> " + position);
                mCurrentPage = position;
                setSelectedPage(position);
            }

            /**
             * 此方法是在状态改变的时候调用。
             * @param state 有三种状态：0, 1, 2
             * state==1 时表示正在滑动，state==2 时表示滑动完毕了，state==0 时表示什么都没做
             * 当页面开始滑动的时候，三种状态的变化顺序为 1 --> 2 --> 0
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mMisScrolled = false;
                        Log.d(TAG, "onPageScrollStateChanged ----------> 1");
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mMisScrolled = true;
                        Log.d(TAG, "onPageScrollStateChanged ----------> 2");
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.d(TAG, "onPageScrollStateChanged ----------> 0");
                        // 在最后一页了，再左滑
                        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !mMisScrolled) {
                            Log.d(TAG, "已经是最后一页了");
                        }
                        // 如果当前页是第一页，再右滑
                        if (mViewPager.getCurrentItem() == 0 && !mMisScrolled) {
                            Log.d(TAG, "已经是第一页了");
                        }
                        mMisScrolled = true;
                        break;
                }
            }
        });
    }

    private void setSelectedPage(int page) {
        if (page < mTextViewList.size()) {
            // Reset all
            for (int i = 0; i < mTextViewList.size(); i++) {
                TextView textView = mTextViewList.get(i);
                textView.setTextColor(getResources().getColor(R.color.color_282828));
            }
            TextView currentTextView = mTextViewList.get(page);
            currentTextView.setTextColor(getResources().getColor(R.color.color_0fbedb));

            final TextView textViewIndicator = findViewById(R.id.text_view_indicator);
            float fromX = textViewIndicator.getX();
            final float toX = currentTextView.getX();
            float toXDelta = toX - fromX;

            final int left = currentTextView.getLeft();
            final int top = textViewIndicator.getTop();
            final int width = currentTextView.getWidth();
            final int height = textViewIndicator.getHeight();

            TranslateAnimation animation = new TranslateAnimation(0, toXDelta, 0, 0);
            animation.setDuration(300);
            textViewIndicator.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    textViewIndicator.clearAnimation();
                    //textViewIndicator.setX(toX);
                    textViewIndicator.layout(left, top, left+width, top+height);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }
}
