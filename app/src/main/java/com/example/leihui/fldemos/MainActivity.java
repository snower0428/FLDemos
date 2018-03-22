package com.example.leihui.fldemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.leihui.fldemos.BottomBarLayout.BottomBarActivity;
import com.example.leihui.fldemos.Dialogs.DialogTestActivity;
import com.example.leihui.fldemos.Downloads.FileDownloadTestActivity;
import com.example.leihui.fldemos.DrawText.DrawTextActivity;
import com.example.leihui.fldemos.DropDown.DropDownTestActivity;
import com.example.leihui.fldemos.Fragments.FragmentTest01;
import com.example.leihui.fldemos.Fragments.FragmentTest02;
import com.example.leihui.fldemos.ViewPager.ViewPagerDemo01Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mDataList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class cls = null;
                switch (position) {
                    case 0:
                        cls = BottomBarActivity.class;
                        break;
                    case 1:
                        cls = FragmentTest01.class;
                        break;
                    case 2:
                        cls = FragmentTest02.class;
                        break;
                    case 3:
                        cls = ViewPagerDemo01Activity.class;
                        break;
                    case 4:
                        cls = DialogTestActivity.class;
                        break;
                    case 5:
                        cls = DropDownTestActivity.class;
                        break;
                    case 6:
                        cls = DrawTextActivity.class;
                        break;
                    case 7:
                        cls = FileDownloadTestActivity.class;
                        break;
                    default:
                        break;
                }
                if (cls != null) {
                    Intent intent = new Intent(MainActivity.this, cls);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadData() {
        mDataList.add("BottomBarLayout");
        mDataList.add("FragmentTest01");
        mDataList.add("FragmentTest02");
        mDataList.add("ViewPagerDemo01");
        mDataList.add("Dialog");
        mDataList.add("DropDown");
        mDataList.add("DrawText");
        mDataList.add("Download");
    }
}
