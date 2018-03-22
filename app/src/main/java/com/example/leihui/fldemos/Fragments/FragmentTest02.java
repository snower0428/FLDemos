package com.example.leihui.fldemos.Fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.leihui.fldemos.R;

public class FragmentTest02 extends AppCompatActivity {

    // 用来标志当前Fragment
    private int mTag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test02);
    }

    public void onReplaceFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        String param = String.valueOf(mTag++);
        bundle.putString("Key", param);
        blankFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_content, blankFragment, param);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onAddFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        String param = String.valueOf(mTag++);
        bundle.putString("Key", param);
        blankFragment.setArguments(bundle);
        // 在添加新的Fragment之前，需要先隐藏前一个Fragment，否则会造成前后层叠
        if (fragmentManager.findFragmentByTag(String.valueOf(mTag-2)) != null) {
            fragmentTransaction.hide(fragmentManager.findFragmentByTag(String.valueOf(mTag-2)));
        }
        fragmentTransaction.add(R.id.fragment_content, blankFragment, param);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onRemoveFragment(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        mTag--;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mTag--;
    }
}
