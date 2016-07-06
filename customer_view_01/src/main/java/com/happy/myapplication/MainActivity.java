package com.happy.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happy.myapplication.view.CreditCircle;
import com.happy.myapplication.view.circleprogress.DountProgressView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Timer mTimer;

    private DountProgressView dountProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreditCircle creditCircle = (CreditCircle) findViewById(R.id.credit_circle);
        creditCircle.setProgress(120);

        dountProgressView = (DountProgressView) findViewById(R.id.dountProgressView);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dountProgressView.setProgress(dountProgressView.getProgress() + 1);
                    }
                });
            }
        }, 1000, 100);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
