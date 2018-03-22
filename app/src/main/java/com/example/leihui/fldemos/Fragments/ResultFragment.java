package com.example.leihui.fldemos.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.leihui.fldemos.R;

/**
 * Created by leihui on 2018/2/26.
 *
 */

public class ResultFragment extends Fragment {

    public ResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    public void showResult(int id) {
        int images[] = {R.drawable.test01, R.drawable.test02, R.drawable.test03};
        View view = getView();
        if (view != null) {
            ImageView imageView = getView().findViewById(R.id.image_view);
            if (imageView != null) {
                imageView.setImageResource(images[id]);
            }
        }
    }
}
