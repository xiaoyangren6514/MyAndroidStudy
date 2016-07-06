package com.happy.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.happy.myapplication.domain.PieData;

import java.util.ArrayList;

/**
 * Created by zhonglq on 2016/6/12.
 */
public class PieView extends View {

    // 颜色表
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private int mStartAngle = 0;

    private int mWidth, mHeight;

    private Paint mPaint;

    private ArrayList<PieData> mPieDatas;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float currentAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mHeight, mWidth) / 2 * 0.5);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentAngle, pieData.getAngle(), true, mPaint);
            currentAngle += pieData.getAngle();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initData() {
        mPieDatas = new ArrayList<>();

        PieData pieData1 = new PieData("语文", 80);
        PieData pieData2 = new PieData("数学", 120);
        PieData pieData3 = new PieData("英语", 70);
        PieData pieData4 = new PieData("文综", 150);
        PieData pieData5 = new PieData("能力", 10);

        mPieDatas.add(pieData1);
        mPieDatas.add(pieData2);
        mPieDatas.add(pieData3);
        mPieDatas.add(pieData4);
        mPieDatas.add(pieData5);

        float sumValues = 0;
        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            sumValues += pieData.getData();

            int j = i % mColors.length;
            pieData.setColor(mColors[j]);
        }

        for (int i = 0; i < mPieDatas.size(); i++) {
            PieData pieData = mPieDatas.get(i);
            float percentage = pieData.getData() / sumValues;
            float angle = percentage * 360;
            pieData.setPercentage(percentage);
            pieData.setAngle(angle);
        }

    }
}
