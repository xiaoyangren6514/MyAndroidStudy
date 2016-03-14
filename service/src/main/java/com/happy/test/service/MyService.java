package com.happy.test.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Service是android四大组件之一。
 * android提供service类来专门创建处理长生命周期的应用程序组件以及不需要用户界面的功能。
 * Android赋予service比处于非活动状态的Activity更高的优先级。和Activity BroadcastReceiver一样，运行在主线程
 * Service主要用于执行Intent查找 处理数据 更新ContentProvider 激活Intent以及触发Notication等。
 * <p/>
 * 在onStartCommand中执行一个service并控制它的启动行为，他的返回值控制当service被终止后，系统如何响应service的重新启动。
 * <p/>
 * 因为service的优先级比较高，通常情况下不会自动停止，因此自终止可以显著的改善应用程序中的资源占用情况。
 * <p/>
 * <p/>
 * 通过startService调用，其生命周期为：
 * 11-09 11:43:45.243 23785-23785/com.happy.test.service I/MyService: ---onCreate---
 * 11-09 11:43:45.243 23785-23785/com.happy.test.service I/MyService: ---onStartCommand---
 * 11-09 11:43:45.243 23785-23785/com.happy.test.service I/MyService: ---onStart---
 * <p/>
 * 11-09 11:44:12.043 23785-23785/com.happy.test.service I/MyService: ---onStartCommand---
 * 11-09 11:44:12.043 23785-23785/com.happy.test.service I/MyService: ---onStart---
 * <p/>
 * 11-09 11:44:15.233 23785-23785/com.happy.test.service I/MyService: ---onDestroy---
 * <p/>
 * onCreate只会在第一次调用时创建，如果以后再次调用startService方法，那么就会略过onCreate方法。
 * <p/>
 * 通过bindService调用，其生命周期为：
 * 11-09 16:48:02.310 12231-12231/com.happy.test.service I/MyService: ---onCreate---
 * 11-09 16:48:02.310 12231-12231/com.happy.test.service I/MyService: ---onBind---
 * <p/>
 * 11-09 16:48:03.130 12231-12231/com.happy.test.service I/MyService: ---onUnbind---
 * 11-09 16:48:03.130 12231-12231/com.happy.test.service I/MyService: ---onDestroy---
 * <p/>
 * 先startService，然后bindService(注：此情况下stopService无效)
 * 11-09 16:45:11.390 9171-9171/com.happy.test.service I/MyService: ---onCreate---
 * 11-09 16:45:11.390 9171-9171/com.happy.test.service I/MyService: ---onStartCommand---
 * <p/>
 * 11-09 16:45:15.050 9171-9171/com.happy.test.service I/MyService: ---onBind---
 * <p/>
 * 11-09 16:45:22.760 9171-9171/com.happy.test.service I/MyService: ---onUnbind---
 * 11-09 16:45:22.760 9171-9171/com.happy.test.service I/MyService: ---onDestroy---
 */
public class MyService extends Service {

    public static final String START_SERVICE_ACTION = "startServiceAction";

    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "---onCreate---");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "---onBind---");
        return myBind;
    }

    private MyBind myBind = new MyBind();

    public class MyBind extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "---onUnbind---");
        return super.onUnbind(intent);
    }

    public void playMusic(String str) {
        Log.i(TAG, str);
    }

    public void stopMusic(String str) {
        Log.i(TAG, str);
    }

    /**
     * 废弃，被onStartCommand取代
     *
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "---onStart---");
    }

    /**
     * 1.android 2.0 API以后引入，提供了和onStart一样的功能，并且还允许你告诉系统，如果系统在显示调用stopService或stopSelf之前终止了service，那么应该如何启动service。
     * 2.实现service的标准模式是从此方法中创建和运行一个新线程，用来在后台执行处理，并在该线程完成后终止这个service
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "---onStartCommand---");
        return Service.START_STICKY;
    }

    /**
     * 不完整代码，尤其是notification
     */
    public void startPlayBack() {
        // 当通知栏被点击时打开的activity
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //设置notification
        Notification notification = new Notification();
        //设置notification持续展示
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        startForeground(0, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "---onDestroy---");
    }
}
