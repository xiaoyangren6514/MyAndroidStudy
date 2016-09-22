package com.happy.xyr.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.happy.xyr.R;
import com.happy.xyr.utils.Utils;

/**
 * Created by zhonglongquan on 2016/9/22.
 */

public class HookView extends View {

    private int progress = 0;

    private float line1_x = 0;
    private float line1_y = 0;
    private float line2_x = 0;
    private float line2_y = 0;

    private int min_size;

    private Paint paint;

    public HookView(Context context) {
        this(context, null);
    }

    public HookView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        min_size = (int) Utils.dp2px(getResources(), 67);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_blue));
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(widthMeasureSpec), measureSpec(heightMeasureSpec));
    }

    private int measureSpec(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = Math.min(size, min_size);
                break;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        progress++;

        /**
         * 绘制圆弧
         */

        int center = getWidth() / 2;
        int center1 = center - getWidth() / 4;
        int radius = getWidth() / 2 - 5;

        RectF rectF = new RectF(center - radius - 1, center - radius - 1, center + radius + 1, center + radius + 1);
        canvas.drawArc(rectF, 0, 360 * progress / 100, false, paint);

        /**
         * 绘制对勾
         */
        if (progress >= 100) {
            if (line1_x <= radius / 3) {
                line1_x = line1_x + 2;
                line1_y = line1_y + 2;
            }
            canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);

            if (line1_x <= radius / 3) {
                line2_x = line1_x;
                line2_y = line1_y;
                line1_x = line1_x + 2;
                line1_y = line1_y + 2;
            }
            if (line1_x >= radius / 3 && line2_x <= radius) {
                line2_x = line2_x + 2;
                line2_y = line2_y - 2;
            }
            canvas.drawLine(center1 + line1_x - 2, center + line1_y, center1 + line2_x, center + line2_y, paint);
        }

        postInvalidate();
    }

}
