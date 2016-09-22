package com.happy.xyr.view.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.happy.xyr.R;
import com.happy.xyr.utils.Utils;

/**
 * Created by zhonglq on 2016/7/6.
 */
public class DountProgressView extends View {

    private static final String TAG = "DountProgressView";
    private Paint mCenterTextPaint;

    private Paint mBottomTextPaint;

    private Paint mFinishCirclePaint;

    private Paint mUnFinishCirclePaint;

    private Paint mInnerCirclePaint;

    private RectF finishOuterRect = new RectF();

    private RectF unFinishOuterRect = new RectF();

    private int default_max = 100;

    private int default_unfinish_circle_color = Color.rgb(204, 204, 204);

    private int default_finish_circle_color = Color.rgb(66, 145, 241);

    private float default_center_textsize;

    private float default_bottom_textsize;

    private int default_center_textcolor = Color.rgb(66, 145, 241);

    private int default_bottom_textcolor = Color.rgb(66, 145, 241);

    private float default_unfinish_circle_width;

    private float default_finish_circle_width;

    private final int default_inner_background_color = Color.TRANSPARENT;

    private int default_start_degree = 0;

    private int inner_background_color;

    private int start_degree;

    private int max;

    private int progress;

    private int min_size;

    private String centerContent;

    private String bottomContent;

    private int unfinish_circle_color;

    private float unfinish_circle_width;

    private int finish_circle_color;

    private float finish_circle_width;

    private float center_textsize;

    private int center_textcolor;

    private float bottom_textsize;

    private int bottom_textcolor;

    private float inner_bottom_text_height;

    public DountProgressView(Context context) {
        this(context, null);
    }

    public DountProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DountProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_center_textsize = Utils.sp2px(getResources(), 18);
        default_bottom_textsize = Utils.sp2px(getResources(), 12);

        min_size = (int) Utils.dp2px(getResources(), 100);

        default_unfinish_circle_width = Utils.dp2px(getResources(), 10);
        default_finish_circle_width = Utils.dp2px(getResources(), 10);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DountProgress, defStyleAttr, 0);
        initTypedArray(typedArray);
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setColor(center_textcolor);
        mCenterTextPaint.setTextSize(center_textsize);

        mBottomTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomTextPaint.setColor(bottom_textcolor);
        mBottomTextPaint.setTextSize(bottom_textsize);

        mUnFinishCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnFinishCirclePaint.setColor(unfinish_circle_color);
        mUnFinishCirclePaint.setStyle(Paint.Style.STROKE);
        mUnFinishCirclePaint.setStrokeWidth(unfinish_circle_width);

        mFinishCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFinishCirclePaint.setStyle(Paint.Style.STROKE);
        mFinishCirclePaint.setStrokeWidth(finish_circle_width);
        mFinishCirclePaint.setColor(finish_circle_color);

        mInnerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCirclePaint.setColor(inner_background_color);

    }

    /**
     * 初始化自定义控件属性信息
     *
     * @param typedArray
     */
    private void initTypedArray(TypedArray typedArray) {
        unfinish_circle_color = typedArray.getColor(R.styleable.DountProgress_dount_unfinish_circle_color, default_unfinish_circle_color);
        unfinish_circle_width = typedArray.getDimension(R.styleable.DountProgress_dount_unfinish_circle_width, default_unfinish_circle_width);

        finish_circle_color = typedArray.getColor(R.styleable.DountProgress_dount_finish_circle_color, default_finish_circle_color);
        finish_circle_width = typedArray.getDimension(R.styleable.DountProgress_dount_finish_circle_width, default_finish_circle_width);

        center_textsize = typedArray.getDimension(R.styleable.DountProgress_dount_center_text_size, default_center_textsize);
        center_textcolor = typedArray.getColor(R.styleable.DountProgress_dount_center_text_color, default_center_textcolor);

        bottom_textsize = typedArray.getDimension(R.styleable.DountProgress_dount_bottom_text_size, default_bottom_textsize);
        bottom_textcolor = typedArray.getColor(R.styleable.DountProgress_dount_bottom_text_color, default_bottom_textcolor);

        centerContent = typedArray.getString(R.styleable.DountProgress_dount_center_text_content);
        bottomContent = typedArray.getString(R.styleable.DountProgress_dount_bottom_text_content);

        progress = typedArray.getInt(R.styleable.DountProgress_dount_progress, 0);
        max = typedArray.getInt(R.styleable.DountProgress_dount_max, default_max);

        start_degree = typedArray.getInt(R.styleable.DountProgress_dount_start_defree, default_start_degree);

        inner_background_color = typedArray.getInt(R.styleable.DountProgress_dount_inner_background_color, default_inner_background_color);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
        inner_bottom_text_height = getHeight() - getHeight() * 3 / 4;
    }

    private int measure(int measureSpec) {
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

        float delta = Math.max(unfinish_circle_width, finish_circle_width);
        finishOuterRect.set(delta, delta, getHeight() - delta, getWidth() - delta);
        unFinishOuterRect.set(delta, delta, getHeight() - delta, getWidth() - delta);

        float innerCircleRadius = getWidth() / 2;
//        float innerCircleRadius = (getWidth() - Math.min(unfinish_circle_width, finish_circle_width) + Math.abs(finish_circle_width - unfinish_circle_width)) / 2f;
//        Log.i(TAG, "width = " + getWidth() + ",unFinishCircleWidth = " + unfinish_circle_width + ",finishCircleWidth = " + finish_circle_width + ",innerCircleRadius = " + innerCircleRadius);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, innerCircleRadius, mInnerCirclePaint);

        canvas.drawArc(finishOuterRect, getStart_degree(), getProgressAngle(), false, mFinishCirclePaint);
        canvas.drawArc(unFinishOuterRect, getStart_degree() + getProgressAngle(), 360 - getProgressAngle(), false, mUnFinishCirclePaint);

        String text = getProgress() + "";
        if (!TextUtils.isEmpty(text)) {
            float textHeight = mCenterTextPaint.descent() + mCenterTextPaint.ascent();
            canvas.drawText(text, (getWidth() - mCenterTextPaint.measureText(text)) / 2, (getHeight() - textHeight) / 2, mCenterTextPaint);
        }

        if (!TextUtils.isEmpty(bottomContent)) {
            float textHeight = mBottomTextPaint.descent() + mBottomTextPaint.ascent();
            float bottomBaseLine = getHeight() - inner_bottom_text_height - textHeight / 2;
            canvas.drawText(bottomContent, (getWidth() - mBottomTextPaint.measureText(bottomContent)) / 2, bottomBaseLine, mBottomTextPaint);
        }

    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360;
    }

    public int getBottom_textcolor() {
        return bottom_textcolor;
    }

    public void setBottom_textcolor(int bottom_textcolor) {
        this.bottom_textcolor = bottom_textcolor;
    }

    public float getBottom_textsize() {
        return bottom_textsize;
    }

    public void setBottom_textsize(float bottom_textsize) {
        this.bottom_textsize = bottom_textsize;
    }

    public String getBottomContent() {
        return bottomContent;
    }

    public void setBottomContent(String bottomContent) {
        this.bottomContent = bottomContent;
    }

    public int getCenter_textcolor() {
        return center_textcolor;
    }

    public void setCenter_textcolor(int center_textcolor) {
        this.center_textcolor = center_textcolor;
    }

    public float getCenter_textsize() {
        return center_textsize;
    }

    public void setCenter_textsize(float center_textsize) {
        this.center_textsize = center_textsize;
    }

    public String getCenterContent() {
        return centerContent;
    }

    public void setCenterContent(String centerContent) {
        this.centerContent = centerContent;
    }

    public int getFinish_circle_color() {
        return finish_circle_color;
    }

    public void setFinish_circle_color(int finish_circle_color) {
        this.finish_circle_color = finish_circle_color;
    }

    public float getFinish_circle_width() {
        return finish_circle_width;
    }

    public void setFinish_circle_width(float finish_circle_width) {
        this.finish_circle_width = finish_circle_width;
    }

    public int getInner_background_color() {
        return inner_background_color;
    }

    public void setInner_background_color(int inner_background_color) {
        this.inner_background_color = inner_background_color;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStart_degree() {
        return start_degree;
    }

    public void setStart_degree(int start_degree) {
        this.start_degree = start_degree;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }
}
