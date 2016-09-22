package com.happy.xyr.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.happy.xyr.R;
import com.happy.xyr.view.CreditCircle;
import com.happy.xyr.view.circleprogress.DountProgressView;

import java.util.Timer;
import java.util.TimerTask;

public class CircleViewActivity extends AppCompatActivity {

    private Timer mTimer;

    private DountProgressView dountProgressView;

    private DountProgressView dountStaticProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        CreditCircle creditCircle = (CreditCircle) findViewById(R.id.credit_circle);
        creditCircle.setProgress(120);

        dountProgressView = (DountProgressView) findViewById(R.id.dountProgressView);
        dountStaticProgressView = (DountProgressView) findViewById(R.id.dountStaticProgressView);
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dountProgressView.setProgress(dountProgressView.getProgress() + 1);
                        if (dountStaticProgressView.getProgress() <= 20) {
                            dountStaticProgressView.setProgress(dountStaticProgressView.getProgress() + 1);
                        }
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
