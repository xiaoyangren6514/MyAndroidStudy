package com.happy.xyr;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhonglq on 2016/6/6.
 */
public class MyAnimView extends View {

    private static final String TAG = "MyAnimView";

    private Point mCurrentPoint;

    private static final int RADIUS = 50;

    private Paint mPaint;

    public MyAnimView(Context context) {
        this(context, null);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaint();
    }

    public MyAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, 200);
//            Log.i(TAG, "200,200");
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(Math.max(200, heightSize), Math.max(200, heightSize));
//            Log.i(TAG, "widthMode == MeasureSpec.AT_MOST :" + Math.max(200, heightSize));
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(Math.max(200, widthSize), Math.max(200, widthSize));
//            Log.i(TAG, "heightMode == MeasureSpec.AT_MOST :" + Math.max(200, widthSize));
//        }
//        setMeasuredDimension(200, 200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentPoint == null) {
            mCurrentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnim();
        } else {
            drawCircle(canvas);
        }
    }

    private void startAnim() {
        Point mStartPoint = new Point(RADIUS, RADIUS);
        Point mEndPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                Point startPoint = (Point) startValue;
                Point endPoint = (Point) endValue;
                float currentX = startPoint.getX() + (endPoint.getX() - startPoint.getX()) * fraction;
                float currentY = startPoint.getY() + (endPoint.getY() - startPoint.getY()) * fraction;
                Point cPoint = new Point(currentX, currentY);
                return cPoint;
            }
        }, mStartPoint, mEndPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(5000);
        animator.start();
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCurrentPoint.getX(), mCurrentPoint.getY(), RADIUS, mPaint);
    }
}
