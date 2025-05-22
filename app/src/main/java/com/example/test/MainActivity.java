package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Bluetooth";

    private final Handler handler = new Handler();

    private TextView textViewTime;


    private final Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            textViewTime.setText(DateTime.getCurrentTime());
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Time
        textViewTime = findViewById(R.id.textViewTime);

        handler.post(updateTimeRunnable);
        //Date
        String currentDate = DateTime.getCurrentDate();
        TextView textViewInfo = findViewById(R.id.textViewInfo);

        if (textViewInfo != null) {
            textViewInfo.setText(currentDate);
        } else {
            Log.e(TAG, "textViewInfo is null!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeRunnable);
    }

}
