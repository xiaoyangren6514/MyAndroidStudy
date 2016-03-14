package com.happy.test.androidstudy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by zhonglq on 2015/12/1.
 */
public class DragView extends View {

    private static final String TAG = "DragView";

    int widthPixels;

    int heightPixels;

    private Scroller mScroller;

    public DragView(Context context) {
        super(context);
        init(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mScroller = new Scroller(context);

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        heightPixels = displayMetrics.heightPixels;
        widthPixels = displayMetrics.widthPixels;
        Log.i(TAG, " displayMetrics.widthPixels " + displayMetrics.widthPixels);
        Log.i(TAG, " displayMetrics.heightPixels " + displayMetrics.heightPixels);
    }

    int lastX = 0;
    int lastY = 0;

    int right;
    int bottom;
    int top;
    int left;
    int measuredWidth;
    int width;
    int height;
    int measuredHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measuredWidth = getMeasuredWidth();
        width = getWidth();
        height = getHeight();
        measuredHeight = getMeasuredHeight();

        Log.i(TAG, " right =  " + right + " ,bottom = " + bottom + " ,top = " + top + " ,left = " + left);
        Log.i(TAG, " measuredWidth =  " + measuredWidth + " ,measuredHeight = " + measuredHeight);
        Log.i(TAG, " width =  " + width + " ,height = " + height);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        right = getRight();
        bottom = getBottom();
        top = getTop();
        left = getLeft();

        Log.d(TAG, " x = " + x + " , y = " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                Log.d(TAG, " lastX = " + lastX + " , lastY = " + lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                int dy = y - lastY;
                Log.d(TAG, " dx = " + dx + " , dy = " + dy);

//                layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);

//                moveBySetLayoutParams(dx, dy);

                ((View) getParent()).scrollBy(-dx, -dy);
//                ((View)getParent()).scrollTo(-(dx+getLeft()),-(dy+getTop()));

//                offsetLeftAndRight(dx);
//                offsetTopAndBottom(dy);

                break;
            case MotionEvent.ACTION_UP:
                View viewGroup = (View) getParent();
                Log.i(TAG, "viewGroup.getScrollX() = " + viewGroup.getScrollX() +
                        "viewGroup.getScrollY() = " + viewGroup.getScrollY() +
                        "-viewGroup.getScrollX() = " + -viewGroup.getScrollX() +
                        "-viewGroup.getScrollY() = " + -viewGroup.getScrollY()
                );
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY()
                );
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void moveBySetLayoutParams(int dx, int dy) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        if (getLeft() + dx > 0) {
            layoutParams.leftMargin = getLeft() + dx;
        } else {
            layoutParams.leftMargin = 0;
        }
        if (getTop() + dy > 0) {
            layoutParams.topMargin = getTop() + dy;
        } else {
            layoutParams.topMargin = 0;
        }
        if (dx + right > widthPixels) {
            layoutParams.leftMargin = widthPixels - width;
        } else {
            layoutParams.rightMargin = right + dx;
        }
        if (dy + bottom > heightPixels) {
            layoutParams.topMargin = heightPixels - height - 45;
        } else {
            layoutParams.bottomMargin = bottom + dy;
        }
        setLayoutParams(layoutParams);
    }
}
