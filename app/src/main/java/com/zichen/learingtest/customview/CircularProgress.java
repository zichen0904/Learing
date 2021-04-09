package com.zichen.learingtest.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.zichen.learingtest.R;

public class CircularProgress extends View {
    private int outorColor = Color.BLUE;
    private int internalColor = Color.RED;
    private int bodrer;
    private int progressTextSize;
    private int progressTextColor;

    private int progressMax = 100;
    private int progress = 30;

    private Paint outorPaint, internalPaint, textPaint;

    public CircularProgress(Context context) {
        this(context, null);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularProgress);
        outorColor = array.getColor(R.styleable.CircularProgress_outrColor, outorColor);
        internalColor = array.getColor(R.styleable.CircularProgress_internalColor, internalColor);
        bodrer = (int) array.getDimension(R.styleable.CircularProgress_border, dip2px(bodrer));
        progressTextSize = array.getDimensionPixelSize(R.styleable.CircularProgress_progressTextSize, sp2px(progressTextSize));
        progressTextColor = array.getColor(R.styleable.CircularProgress_progressTextColor, progressTextColor);
        array.recycle();

        //外圆
        outorPaint = new Paint();
        outorPaint.setAntiAlias(true);
        outorPaint.setStrokeWidth(bodrer);
        outorPaint.setDither(true);
        outorPaint.setColor(outorColor);
        outorPaint.setStyle(Paint.Style.STROKE);//空心

        //内圆
        internalPaint = new Paint();
        internalPaint.setAntiAlias(true);
        internalPaint.setStrokeWidth(bodrer);
        internalPaint.setDither(true);
        internalPaint.setColor(internalColor);
        internalPaint.setStyle(Paint.Style.STROKE);


        //文字
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(progressTextSize);
        textPaint.setDither(true);
        textPaint.setColor(progressTextColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));//保证是正方形
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //内圆
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, center - bodrer / 2, internalPaint);
        float percent = (float) progress / progressMax;
        if (progressMax == 0) return;
        RectF rectF = new RectF(bodrer / 2, bodrer / 2, getWidth() - bodrer / 2, getWidth() - bodrer / 2);
        canvas.drawArc(rectF, 0, percent * 360, false, outorPaint);

        //文字
        String text = ((int)(percent * 100)) + "%";
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        int x = getWidth() / 2 - textBounds.width() / 2;
        Paint.FontMetricsInt metricsInt = textPaint.getFontMetricsInt();
        int dy = (metricsInt.bottom - metricsInt.top) / 2 - metricsInt.bottom;
        int y = getHeight() / 2 + dy;
        canvas.drawText(text, x, y, textPaint);

    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
