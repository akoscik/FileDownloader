package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActicity";
    private Button button;
    private volatile boolean stopThread = false;
    private TextView downloadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        downloadText = findViewById(R.id.textView);
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                button.setText("Downloading...");
            }
        });

        for (int downloadProgress = 0; downloadProgress <=100; downloadProgress=downloadProgress+10){
            if (stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        button.setText("Start");
                    }
                });
                return;
            }
            Log.d(TAG, "Download Progress: " + downloadProgress +"%");
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    downloadText.setText("Download Progress: " + String.valueOf(finalDownloadProgress) + "%");
                }
            });
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                button.setText("Start");
            }
        });
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    public void stopDownload(View view){
        downloadText.setText("Download Stopped");
        stopThread = true;
    }

    class ExampleRunnable implements Runnable{
        @Override
        public void run(){
            mockFileDownloader();
        }
    }
}