package com.myproject.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myproject.interf.OnToggleStateChangedListener;

/**
 * Created by Administrator on 2015/7/1.
 * Used for 自定义开关
 */
public class ToggleView extends View {
    private Bitmap switchBackgroundBitmap = null;//背景图片
    private Bitmap slideButtonBackgroundBitmap = null;//背景图片
    private boolean currentToggleState = false;//滑动块当前的状态，默认为：关闭
    private boolean isTouching = false; // 是否正在触摸中, 默认值: 未触摸
    private int currentX; // x轴最新的偏移量
    private OnToggleStateChangedListener mOnToggleStateChangedListener;
    String namespace = null;
    int switchBackgroundID = -1;
    int slideButtonBackgroundID = -1;

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //自定义属性的命名空间
        namespace = "http://schemas.android.com/apk/res/com.myproject";
        //取出自定义的属性
        //1、当前的状态
        currentToggleState = attrs.getAttributeBooleanValue(namespace, "currentState", false);
        //2、取出背景图片的id
        switchBackgroundID = attrs.getAttributeIntValue(namespace, "switchBackgroundID", -1);
        setSwitchBackgroundID(switchBackgroundID);
        //取出滑动块图片的id
        slideButtonBackgroundID = attrs.getAttributeIntValue(namespace, "slideButtonBackgroundID", -1);
        setSlideButtonBackgroundID(slideButtonBackgroundID);
    }

    /**
     * 当android系统需要绘制此控件时，回调此方法
     *
     * @param canvas 画画板
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(switchBackgroundBitmap, 0, 0, null);//把背景图片画到控件的左上角上
        int left = switchBackgroundBitmap.getWidth() - slideButtonBackgroundBitmap.getWidth();// 打卡状态的左边界的x轴地址
        if (isTouching) {// 当前手指正在触摸中, 根据当前的x轴的位置绘制滑动块的位置.
            int slidingLeft = currentX - slideButtonBackgroundBitmap.getWidth() / 2;// 正在滑动中的滑动块的左边的值
            if (slidingLeft < 0) {// 超出了左边界, 一直赋值为0
                slidingLeft = 0;
            } else if (slidingLeft > 0) {// 如果当前的滑动块的左边 大于了 背景图片宽度 - 滑动块宽度 , 超出了边界.
                slidingLeft = left;
            }
            canvas.drawBitmap(slideButtonBackgroundBitmap, slidingLeft, 0, null);
        } else {
            // 没有触摸滑动块, 根据currentToggleState的状态绘制滑动块的位置
            if (currentToggleState) {
                canvas.drawBitmap(slideButtonBackgroundBitmap, left, 0, null); // 当前是打开的状态: top=0, left=背景图片的宽度 - 滑动块的宽度
            } else {
                canvas.drawBitmap(slideButtonBackgroundBitmap, 0, 0, null); // 当前是关闭的状态: top=0, left=0
            }
        }
    }


    /**
     * 当android系统需要测量此控件时，回调此方法。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                isTouching = true;
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE://移动
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP://抬起
                isTouching = false;
                currentX = (int) event.getX();
                // 背景的中心点, 当前的x轴的偏移量
                boolean state = currentX > (switchBackgroundBitmap.getWidth() / 2);
                // 回调用户的事件
                if (state != currentToggleState && mOnToggleStateChangedListener != null) {
                    mOnToggleStateChangedListener.OnToggleStateCHanged(state);
                }

                currentToggleState = state;
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    /**
     * alt + shift + J
     * 设置开关的状态
     *
     * @param b
     */
    public void setCurrentToggleState(boolean b) {
        currentToggleState = b;
    }

    /**
     * 设置背景图片的id
     *
     * @param switchBackgroundID
     */
    private void setSwitchBackgroundID(int switchBackgroundID) {
        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), switchBackgroundID);
    }

    /**
     * 设置滑动块的背景
     *
     * @param slideButtonBackground
     */
    public void setSlideButtonBackgroundID(int slideButtonBackground) {
        slideButtonBackgroundBitmap = BitmapFactory.decodeResource(getResources(), slideButtonBackground);
    }

    public void setOnToggleStateChangedListener(OnToggleStateChangedListener listener) {
        mOnToggleStateChangedListener = listener;
    }

}
