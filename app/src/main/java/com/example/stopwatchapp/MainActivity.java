package com.example.stopwatchapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnPause, btnReset;

    private long startTime = 0L;
    private long timeBuff = 0L;
    private long updateTime = 0L;
    private boolean isRunning = false;

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime = System.currentTimeMillis() - startTime;
                long millis = updateTime + timeBuff;

                int minutes = (int) (millis / 60000);
                int seconds = (int) (millis / 1000) % 60;
                int milliseconds = (int) (millis % 1000);

                String time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
                tvTimer.setText(time);

                handler.postDelayed(this, 10);
            }
        };

        btnStart.setOnClickListener(v -> {
            if (!isRunning) {
                startTime = System.currentTimeMillis();
                handler.postDelayed(runnable, 0);
                isRunning = true;
            }
        });

        btnPause.setOnClickListener(v -> {
            if (isRunning) {
                timeBuff += System.currentTimeMillis() - startTime;
                handler.removeCallbacks(runnable);
                isRunning = false;
            }
        });

        btnReset.setOnClickListener(v -> {
            handler.removeCallbacks(runnable);
            isRunning = false;
            startTime = 0L;
            timeBuff = 0L;
            updateTime = 0L;
            tvTimer.setText("00:00:000");
        });
    }
}
