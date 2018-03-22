package com.example.leihui.fldemos.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.leihui.fldemos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leihui on 2018/3/2.
 *
 */

public class DialogFragmentTest extends DialogFragment {

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
    private DialogItemAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_fragment_test, container);
        mListView = view.findViewById(R.id.list_view);
        loadData();
        mAdapter = new DialogItemAdapter(getContext(), mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.checkItem(position);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window window = getDialog().getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setLayout((int) (dm.widthPixels * 0.75), (int) (dm.heightPixels * 0.6));
            }
        }
    }

    private void loadData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < mData.length; i++) {
            mDataList.add(mData[i]);
        }
    }
}
