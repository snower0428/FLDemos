package com.example.leihui.fldemos.Dialogs;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leihui.fldemos.R;

public class DialogTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_test);

        Button btnDialogFragment = findViewById(R.id.btn_dialog_fragment);
        btnDialogFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        DialogFragmentTest dialogFragmentTest = new DialogFragmentTest();
        dialogFragmentTest.show(getSupportFragmentManager(), "DialogFragmentTest");
    }
}
