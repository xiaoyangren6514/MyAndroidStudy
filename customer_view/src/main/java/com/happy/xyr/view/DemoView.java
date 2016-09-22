package com.happy.xyr.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhonglq on 2016/6/12.
 */
public class DemoView extends View {

    private Paint mPaint;

    public DemoView(Context context) {
        this(context, null);
    }

    public DemoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
//        drawArc(canvas);
//        drawOval(canvas);
//        drawPoint(canvas);
//        drawLine(canvas);
//        drawRect(canvas);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(200,200,50,mPaint);

        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200,400,50,mPaint);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(200,600,50,mPaint);
    }

    /**
     * 绘制圆弧
     *
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawArc(Canvas canvas) {
        RectF rectF1 = new RectF(100, 100, 500, 300);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF1, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF1, 0, 90, false, mPaint);

        RectF rectF2 = new RectF(100, 500, 500, 700);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF2, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF2, 0, 90, true, mPaint);

    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        RectF oval = new RectF(100, 500, 400, 700);
        canvas.drawOval(oval, mPaint);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawRect(Canvas canvas) {
//        canvas.drawRect(100, 100, 500, 500, mPaint);
//        Rect rect = new Rect(800, 800, 1000, 1000);
//        canvas.drawRect(rect, mPaint);
        canvas.drawRoundRect(100, 100, 600, 300, 60, 30, mPaint);
    }

    /**
     * 绘制直线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
//        canvas.drawLine(100, 100, 100, 500, mPaint);
//        canvas.drawLines(new float[]{200, 200, 400, 400,
//                600, 600, 800, 800}, mPaint);
        canvas.drawLines(new float[]{
                100, 100, 600, 100,
                600, 100, 100, 600,
                100, 600, 350, 50,
                350, 50, 600, 600,
                600, 600, 100, 100
        }, mPaint);
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        canvas.drawPoints(new float[]{200, 100, 200, 200}, mPaint);
        canvas.drawPoint(10, 10, mPaint);
    }
}
