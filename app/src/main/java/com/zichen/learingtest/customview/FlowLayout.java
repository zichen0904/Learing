package com.zichen.learingtest.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bianzichen.
 * Date: 2021-04-21
 */
public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = 20;
    private List<List<View>> allLines = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int selfWith = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);

        int parentNeedWidth = 0;
        int parentNeedHeight = 0;

        //先度量子view
        int childCount = getChildCount();
        int paddLeft = getPaddingLeft();
        int paddRight = getPaddingRight();
        int paddTop = getPaddingTop();
        int paddBottom = getPaddingBottom();
        List<View> linViews = new ArrayList<>();
        int lineWithUsed = 0;
        int lineHeightUsed = 0;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //将layoutParams转化为measureSpec
            LayoutParams layoutParams = childView.getLayoutParams();
            int widthChildSpec = getChildMeasureSpec(widthMeasureSpec, paddLeft + paddRight, layoutParams.width);
            int heightChildSpec = getChildMeasureSpec(heightMeasureSpec, paddTop + paddBottom, layoutParams.height);
            childView.measure(widthChildSpec, heightChildSpec);

            //获取子view宽高；
            int childViewWith = childView.getMeasuredWidth();
            int childViewHeight = childView.getMeasuredHeight();

            if (lineWithUsed + childViewWith + mHorizontalSpacing > selfWith) {//超过最大宽度  换行
                allLines.add(linViews);
                lineHeights.add(lineHeightUsed);

                parentNeedWidth = Math.max(parentNeedWidth, lineWithUsed + mHorizontalSpacing);
                parentNeedHeight = parentNeedHeight + lineHeightUsed + mHorizontalSpacing;

                linViews = new ArrayList<>();
                lineWithUsed = 0;
                lineWithUsed = 0;
            }

            linViews.add(childView);
            lineWithUsed = childViewWith + lineWithUsed + mHorizontalSpacing;
            lineHeightUsed = Math.max(lineHeightUsed, childViewHeight);

            if (i == childCount - 1) {
                allLines.add(linViews);
                lineHeights.add(lineHeightUsed);
                parentNeedWidth = Math.max(parentNeedWidth, lineWithUsed + mHorizontalSpacing);
                parentNeedHeight = parentNeedHeight + lineHeightUsed + mHorizontalSpacing;
            }

        }
        //度量自身并保存
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int releaWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWith : parentNeedWidth;
        int releaHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeedHeight;
        setMeasuredDimension(releaWidth, releaHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();
        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view=lineViews.get(j);
//                int
//                view.layout();
            }
        }

    }
}
