package com.zichen.learingtest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.Button;

import com.zichen.learingtest.customview.ColorTarckTextView;

public class SecondActicity extends BaseActivity {
    private ColorTarckTextView tarckTextView;
    private Button leftToRight, rightToLeft;

    @Override
    protected void init() {
        tarckTextView=findViewById(R.id.tarck_tv);
        leftToRight=findViewById(R.id.left_to_right);
        rightToLeft=findViewById(R.id.right_to_left);
    }

    @Override
    protected void setOnclickListener() {
        leftToRight.setOnClickListener(this);
        rightToLeft.setOnClickListener(this);
    }

    @Override
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.left_to_right:
                tarckTextView.setDirection(ColorTarckTextView.Direction.LEFT_TO_RIGHT);
                ValueAnimator animatorLeft = ObjectAnimator.ofFloat(0, 3);
                animatorLeft.setDuration(2000);
                animatorLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        tarckTextView.setProgress(progress);
                    }
                });
                animatorLeft.start();
                break;
            case R.id.right_to_left:
                tarckTextView.setDirection(ColorTarckTextView.Direction.RIGHT_TO_LEFT);
                ValueAnimator animatorRight = ObjectAnimator.ofFloat(0, 3);
                animatorRight.setDuration(2000);
                animatorRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float progress = (float) animation.getAnimatedValue();
                        tarckTextView.setProgress(progress);
                    }
                });
                animatorRight.start();
                break;
        }

    }

    @Override
    public void success() {

    }

    @Override
    public void error() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_second;
    }

}
