package com.example.leihui.fldemos.Downloads;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.leihui.fldemos.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

public class FileDownloadTestActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download_test);

        FileDownloader.setup(this);

        Button btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownload();
            }
        });
    }

    private void onDownload() {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "Download" + File.separator + "test" + File.separator + "test.apk";
        File file = new File(path);
        if (file.exists()) {
            Log.d(TAG, "exists");
        }
        String strUrl = "http://res.ifjing.com/ops/com.nd.android.pandahome2_9.2(9201)_1010311p_0e996f62251a4231a210d52f1e715e1f.apk";
        FileDownloader.getImpl().create(strUrl)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "pending --- soFarBytes:" + soFarBytes + " --- totalBytes:" + totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "progress --- soFarBytes:" + soFarBytes + " --- totalBytes:" + totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d(TAG, "completed");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "paused --- soFarBytes:" + soFarBytes + " --- totalBytes:" + totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d(TAG, "error");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d(TAG, "warn");
                    }
                }).start();
    }

}
