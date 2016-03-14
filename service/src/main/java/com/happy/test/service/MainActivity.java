package com.happy.test.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

/**
 * 启动service的两种方式：
 * 1、通过startService
 * 一旦开启，除非我们调用stopService或unbindService，否则会一直处于运行中状态
 * 与启动它的activity没有关系，无法调用service中的方法
 * 2、通过bindService
 * 要让一个Service支持绑定，需要实现onBind方法，并返回当前被绑定service的实例
 * Service与Activity绑定，后者拥有对前者的实例实例引用，可以对service中的方法进行调用
 * Service与其它组件的连接表示为一个ServiceConnection，通过重写onServiceConnected和onServiceDisconnected获得对service的引用
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button btnStartService;

    private Button btnStopService;

    private Button bindService;

    private Button unbindService;

    private Button playMusic;

    private Button stopMusic;

    private Intent startServiceIntent;

    private MyService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = (Button) this.findViewById(R.id.btnStartService);
        btnStopService = (Button) this.findViewById(R.id.btnStopService);
        bindService = (Button) this.findViewById(R.id.bindService);
        unbindService = (Button) this.findViewById(R.id.unbindService);
        playMusic = (Button) this.findViewById(R.id.playMusic);
        stopMusic = (Button) this.findViewById(R.id.stopMusic);

        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        playMusic.setOnClickListener(this);
        stopMusic.setOnClickListener(this);

        //显示启动停止service
        startServiceIntent = new Intent(this, MyService.class);
//        startService(startServiceIntent);
//        stopService(startServiceIntent);

        //隐士启动停止service
//        startServiceIntent = new Intent(MyService.START_SERVICE_ACTION);
//        startService(startServiceIntent);
//        stopService(startServiceIntent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                startService(startServiceIntent);
                break;
            case R.id.btnStopService:
                stopService(startServiceIntent);
                break;
            case R.id.bindService:
                bindService(startServiceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unbindService:
                unbindService(serviceConnection);
                break;
            case R.id.playMusic:
                mService.playMusic("月亮之上");
                break;
            case R.id.stopMusic:
                mService.stopMusic("月亮之上");
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当建立连接时调用
            mService = ((MyService.MyBind) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 当service意外断开时接收
            mService = null;
        }
    };

}
