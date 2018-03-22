package com.example.leihui.fldemos.ViewPager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leihui.fldemos.R;

/**
 * Created by leihui on 2018/3/1.
 *
 */

public class Frag2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_2, container, false);
        TextView textView = view.findViewById(R.id.text_view);
        if (textView != null) {
            String strText = "I am 02";
            textView.setText(strText);
        }
        return view;
    }
}
