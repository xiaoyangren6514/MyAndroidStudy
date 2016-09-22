package com.happy.xyr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zhonglq on 2016/6/12.
 */
public class CanvasOpView extends View {

    private static final String TAG = "CanvasOpView";

    private Paint mPaint;

    private int mHight;

    private int mWidth;

    public CanvasOpView(Context context) {
        this(context, null);
    }

    public CanvasOpView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasOpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
//        translate(canvas);
        rotate(canvas);

    }

    private void rotate(Canvas canvas) {
        canvas.translate(mWidth / 2, mHight / 2);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(0, 0, 400, mPaint);
        canvas.drawCircle(0, 0, 380, mPaint);
        for (int i = 0; i < 360; i += 10) {
            canvas.drawLine(0, 400, 0, 380, mPaint);
            canvas.rotate(10);
        }
//        canvas.translate(mWidth/2,mHight/2);
//        RectF rectF = new RectF(0,-400,400,0);
//        canvas.drawRect(rectF,mPaint);
//        canvas.rotate(180);
//        canvas.drawRect(rectF,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged");
        mWidth = w;
        mHight = h;
    }

    private void translate(Canvas canvas) {
        canvas.translate(200, 200);
        canvas.drawCircle(0, 0, 50, mPaint);

        canvas.translate(200, 200);
        canvas.drawCircle(0, 0, 100, mPaint);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(30);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
    }
}
