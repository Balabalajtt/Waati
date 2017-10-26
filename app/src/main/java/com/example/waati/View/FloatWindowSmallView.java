package com.example.waati.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.waati.Manager.MyWindowManager;
import com.example.waati.R;

import java.lang.reflect.Field;

/**
 * Created by 江婷婷 on 2017/10/25.
 */

public class FloatWindowSmallView extends LinearLayout {
    public static int viewWidth;
    public static int viewHeight;

    private static int statusBarHeight;

    private WindowManager windowManager;

    private WindowManager.LayoutParams mParams;

    private float xInScreen;
    private float yInScreen;

    private float xDownInScreen;
    private float yDownInScreen;

    private float xInView;
    private float yInView;

    public FloatWindowSmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window_small, this);
        View view = findViewById(R.id.small_window_layout);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        TextView percentView = (TextView) findViewById(R.id.percent);
//        percentView.setText(MyWindowManager.getUsedPercentValue(context));
        percentView.setText("粑帅粑");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    openBigWindow();
                } else if (yInScreen > windowManager.getDefaultDisplay().getHeight() * 4 / 5) {
                    final float startY = yInScreen;
                    final float deltaY = yInScreen - windowManager.getDefaultDisplay().getHeight() * 1 / 50;
                    final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float fraction = animator.getAnimatedFraction();
                            yInScreen = startY - deltaY * fraction;
                            Log.d("111", "onAnimationUpdate: " + 111);
                            updateViewPosition();
                        }
                    });
                    animator.start();
                } else  {
                    final float startX = xInScreen;
                    final float deltaX;
                    if (xInScreen > windowManager.getDefaultDisplay().getWidth() / 2) {
                        deltaX = windowManager.getDefaultDisplay().getWidth() - startX;
                    } else {
                        deltaX = -startX;
                    }
                    final ValueAnimator animator = ValueAnimator.ofInt(0, 1).setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float fraction = animator.getAnimatedFraction();
                            xInScreen = startX + deltaX * fraction;
                            Log.d("111", "onAnimationUpdate: " + 111);
                            updateViewPosition();
                        }
                    });
                    animator.start();
//                    xInScreen = 0;
//                    updateViewPosition();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params 小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 打开大悬浮窗，同时关闭小悬浮窗。
     */
    private void openBigWindow() {
        MyWindowManager.createBigWindow(getContext());
        MyWindowManager.removeSmallWindow(getContext());
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

}