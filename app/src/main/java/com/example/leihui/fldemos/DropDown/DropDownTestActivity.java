package com.example.leihui.fldemos.DropDown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.leihui.fldemos.R;

import java.util.ArrayList;
import java.util.List;

public class DropDownTestActivity extends AppCompatActivity {

    private String[] mData = {
            "第1-50章",
            "第51-100章",
            "第101-150章",
            "第151-200章",
            "第201-250章",
            "第251-300章",
            "第301-350章",
            "第351-400章",
            "第401-450章",
            "第451-500章",
            "第501-550章",
            "第551-600章",
            "第601-650章",
            "第651-700章",
            "第701-750章",
            "第751-800章",
            "第801-850章",
            "第851-900章",
            "第901-950章",
            "第951-1000章",
            "第1001-1050章",
            "第1051-1100章",
            "第1101-1150章",
            "第1151-1200章",
            "第1201-1250章",
            "第1251-1300章",
            "第1301-1350章",
            "第1351-1400章",
            "第1401-1450章",
            "第1451-1500章",
            "第1501-1550章",
            "第1551-1600章",
            "第1601-1650章",
            "第1651-1700章",
            "第1701-1750章",
            "第1751-1790章",
    };

    private List<String> mDataList;
    private ListView mListView;
    private ChapterItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_test);

        loadData();

        RelativeLayout currentChaptersLayout = findViewById(R.id.current_chapters_layout);
        if (currentChaptersLayout != null) {
            currentChaptersLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListView.setVisibility(View.VISIBLE);
                }
            });
        }

        mListView = findViewById(R.id.list_view);
        mAdapter = new ChapterItemAdapter(this, mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setVisibility(View.GONE);
            }
        });
        mListView.post(new Runnable() {
            @Override
            public void run() {
                if (mDataList.size() > 5) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mListView.getLayoutParams();
                    View listItem = mAdapter.getView(0, null, mListView);
                    listItem.measure(0, 0);
                    int mListHeight = listItem.getMeasuredHeight();
                    layoutParams.height = mListHeight * 5;
                    mListView.setLayoutParams(layoutParams);
                }
            }
        });
    }

    private void loadData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < mData.length; i++) {
            mDataList.add(mData[i]);
        }
    }
}
