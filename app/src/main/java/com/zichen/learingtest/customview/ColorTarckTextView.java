package com.zichen.learingtest.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zichen.learingtest.R;

@SuppressLint("AppCompatCustomView")
public class ColorTarckTextView extends TextView {
    private static String TAG = "TAG";

    private Paint originPaint, changePaint;
    private float mCurrentProgress = 0.5f;
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public ColorTarckTextView(Context context) {
        this(context, null);
    }

    public ColorTarckTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTarckTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTarckTextView);
        int originColor = array.getColor(R.styleable.ColorTarckTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTarckTextView_changeColor, getTextColors().getDefaultColor());
        array.recycle();
        originPaint = getPaintByColor(originColor);
        changePaint = getPaintByColor(changeColor);
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿
        paint.setColor(color);
        paint.setDither(true);//防抖动
        paint.setTextSize(getTextSize());
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);        //不调用父类的方法
        int middle = (int) (mCurrentProgress * getWidth());
        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, changePaint, 0, middle);
            drawText(canvas, originPaint, middle, getWidth());
        } else {
            drawText(canvas, changePaint, getWidth() - middle, getWidth());
            drawText(canvas, originPaint, 0, getWidth() - middle);
        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);//裁剪
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;//view宽度/2 - (文字宽度/2+padding)
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int y = getHeight() / 2 + dy;//基线
        canvas.drawText(text, x, y, paint);//还是只有一种颜色
        canvas.restore();
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public void setProgress(float progress) {
        this.mCurrentProgress = progress;
        Log.e(TAG, "setProgress: " + progress);
        invalidate();
    }


}
