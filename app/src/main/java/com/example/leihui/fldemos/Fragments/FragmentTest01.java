package com.example.leihui.fldemos.Fragments;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.leihui.fldemos.R;

public class FragmentTest01 extends AppCompatActivity implements SelectFragment.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test01);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectFragment selectFragment = (SelectFragment) fragmentManager.findFragmentById(R.id.fragment_select);
        if (selectFragment != null) {
            selectFragment.selectFirstItem();
        }
    }

    @Override
    public void onItemSelected(int id) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ResultFragment resultFragment = (ResultFragment) fragmentManager.findFragmentById(R.id.fragment_result);
        if (resultFragment != null) {
            resultFragment.showResult(id);
        }
    }
}
