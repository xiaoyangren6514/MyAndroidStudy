package com.happy.xyr.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.happy.xyr.R;

/**
 * Created by zhonglq on 2016/7/6.
 */
public class TestActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "TestActivity";

    private RelativeLayout rlParent;

    GestureDetector gestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_test);
        rlParent = (RelativeLayout) findViewById(R.id.rlParent);
        rlParent.setOnTouchListener(this);
        //获取用户配置信息 locale scaling，获取设备信息 输入模式 屏幕大小  屏幕方向
        Configuration configuration = getResources().getConfiguration();
        int orientation = configuration.orientation;
        //提供了自定义控件的常用的标准常量  尺寸大小 滑动距离 敏感度等
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        int scaledMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        boolean b = viewConfiguration.hasPermanentMenuKey();
        int doubleTapTimeout = viewConfiguration.getDoubleTapTimeout();
        int longPressTimeout = viewConfiguration.getLongPressTimeout();
        int keyRepeatTimeout = viewConfiguration.getKeyRepeatTimeout();

        gestureDetector = new GestureDetector(this, new GestureListenerImpl());

    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }*/

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListenerImpl implements GestureDetector.OnGestureListener {

        /**
         * 触摸屏幕时调用
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "----onDown----");
            return false;
        }


        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "----onShowPress----");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "----onSingleTapUp----");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "----onScroll----");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "----onLongPress----");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "----onFling----");
            return false;
        }
    }

}
