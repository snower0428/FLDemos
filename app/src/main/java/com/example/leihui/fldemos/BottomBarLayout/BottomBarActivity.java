package com.example.leihui.fldemos.BottomBarLayout;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.leihui.fldemos.R;

import java.util.ArrayList;

public class BottomBarActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<View> mViewList = new ArrayList<>();
    private BottomBarPageAdapter mAdapter;
    private BottomBarLayout mBottomBarLayout;
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        LayoutInflater layoutInflater = getLayoutInflater();
        mViewList.add(layoutInflater.inflate(R.layout.page_view_home, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.page_view_video, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.page_view_micro, null, false));
        mViewList.add(layoutInflater.inflate(R.layout.page_view_mine, null, false));

        mViewPager = findViewById(R.id.view_pager);
        mAdapter = new BottomBarPageAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);

        mBottomBarLayout = findViewById(R.id.bottom_bar_layout);
        mBottomBarLayout.setViewPager(mViewPager);
        mBottomBarLayout.setSmoothScroll(true);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int position) {
                if (position == 0 && mBottomBarLayout.getCurrentItem() == position) {
                    bottomBarItem.setIconSelectedResourceId(R.drawable.tab_loading);
                    bottomBarItem.setStatus(true);

                    // 播放旋转动画
                    if (mRotateAnimation == null) {
                        mRotateAnimation = new RotateAnimation(0, 360,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        mRotateAnimation.setDuration(800);
                        mRotateAnimation.setRepeatCount(-1);
                    }
                    ImageView bottomImageView = bottomBarItem.getImageView();
                    bottomImageView.setAnimation(mRotateAnimation);
                    bottomImageView.startAnimation(mRotateAnimation);

                    // 模拟数据刷新完毕
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomBarItem.setIconSelectedResourceId(R.drawable.tab_home_selected);
                            bottomBarItem.setStatus(true);
                            cancelTabLoading(bottomBarItem);
                        }
                    }, 3000);
                    return;
                }

                // 如果点击了其他条目，换回原来的图标
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.drawable.tab_home_selected);
                cancelTabLoading(bottomItem);
            }
        });
        //mBottomBarLayout.setUnread(0, 20);
        //mBottomBarLayout.setUnread(1, 101);
        //mBottomBarLayout.showNotify(2);
        //mBottomBarLayout.setMsg(3, "NEW");
    }

    private void cancelTabLoading(BottomBarItem bottomBarItem) {
        Animation animation = bottomBarItem.getImageView().getAnimation();
        if (animation != null) {
            animation.cancel();
        }
    }
}
