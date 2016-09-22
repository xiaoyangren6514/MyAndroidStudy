package com.happy.xyr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.happy.xyr.R;


public class CreditCircle extends View {

    private static final String TAG = "CreditCircle";

    public CreditCircle(Context context) {
        super(context);
        init();
    }

    public CreditCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    Paint mpaint;
    int radiusbig;
    int circlewidth;
    double mprogress = 1;

    private void init() {
        radiusbig = this.getResources().getDimensionPixelOffset(R.dimen.circle_raduis);
        circlewidth = this.getResources().getDimensionPixelOffset(R.dimen.circle_width);
        mpaint = new Paint();
        mpaint.setColor(Color.parseColor("#f7f7f7"));
        mpaint.setAntiAlias(true);
    }

    public void setProgress(double progress) {
        mprogress = progress;
        postInvalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        getDrawingRect(rect);

        int radius = radiusbig / 2 - circlewidth / 2;
        //圆形
        int left = rect.centerX() - radius;
        int top = rect.centerY() - radius;
        int right = left + radius * 2;
        int bottom = top + radius * 2;
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(circlewidth);
        RectF rectF = new RectF(left, top, right, bottom);
        canvas.drawArc(rectF, 0, 360, true, mpaint);

        //圆形外线
        int outleft = left - circlewidth / 2;
        int outtop = top - circlewidth / 2;
        int ourRight = right + circlewidth / 2;
        int outbottom = bottom + circlewidth / 2;
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(1);
        mpaint.setColor(Color.parseColor("#f4f2f2"));
        RectF rectout = new RectF(outleft, outtop, ourRight, outbottom);
        canvas.drawArc(rectout, 0, 360, true, mpaint);

        //圆形内线
        int insideleft = left + circlewidth / 2;
        int insidetop = top + circlewidth / 2;
        int insideright = right - circlewidth / 2;
        int insidebottom = bottom - circlewidth / 2;
        RectF rectinside = new RectF(insideleft, insidetop, insideright, insidebottom);
        canvas.drawArc(rectinside, 0, 360, true, mpaint);

        //进度
        float arg = (float) (360 * mprogress);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.parseColor("#21bee0"));
        mpaint.setStrokeWidth(circlewidth);
        canvas.drawArc(rectF, 270, arg, false, mpaint);

        if (mprogress < 1 && mprogress > 0) {
            mpaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mpaint.setStrokeWidth(1f);
            //进度开始的圆形
            float startx = (float) (rect.centerX() + radius * Math.sin(0));
            float starty = (float) (rect.centerY() - radius * Math.cos(0));
            int radus = circlewidth / 2;
            canvas.drawCircle(startx, starty, radus, mpaint);
            float startx1 = (float) (rect.centerX() + radius * Math.sin(Math.toRadians(arg)));
            float starty1 = (float) (rect.centerY() - radius * Math.cos(Math.toRadians(arg)));
            canvas.drawCircle(startx1, starty1, radus, mpaint);
        }

        canvas.save();
    }
}
