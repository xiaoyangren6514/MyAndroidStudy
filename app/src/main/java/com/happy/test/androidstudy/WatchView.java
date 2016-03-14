package com.happy.test.androidstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhonglq on 2015/12/2.
 */
public class WatchView extends View {

    private Context mContext;

    private Paint mCirclePaint;

    private Paint mDegreePaint;

    private Paint mTextPaint;

    private int screenWidth;

    private int screenHeight;

    private int radius;

    public WatchView(Context context) {
        this(context, null);
    }

    public WatchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;

        radius = screenWidth / 2;

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(2);

        mDegreePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDegreePaint.setStyle(Paint.Style.STROKE);
        mDegreePaint.setStrokeWidth(5);
        mDegreePaint.setColor(Color.BLUE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(14);
        mTextPaint.setColor(Color.BLACK);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, screenWidth / 2, mCirclePaint);
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                mDegreePaint.setStrokeWidth(5);
                canvas.drawLine(screenWidth / 2, screenHeight / 2 - radius, screenWidth / 2, screenHeight / 2 - radius + 60, mDegreePaint);
                canvas.drawText(String.valueOf(i), screenWidth / 2 - mDegreePaint.measureText(String.valueOf(i)) / 2, screenHeight / 2 - radius + 80, mTextPaint);
            } else {
                mDegreePaint.setStrokeWidth(3);
                canvas.drawLine(screenWidth / 2, screenHeight / 2 - radius, screenWidth / 2, screenHeight / 2 - radius + 30, mDegreePaint);
                canvas.drawText(String.valueOf(i), screenWidth / 2 - mDegreePaint.measureText(String.valueOf(i)) / 2, screenHeight / 2 - radius + 80, mTextPaint);
            }
            canvas.rotate(15, screenWidth / 2, screenHeight / 2);
        }
    }
}
