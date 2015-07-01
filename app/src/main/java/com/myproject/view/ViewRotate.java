package com.myproject.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.myproject.R;

public class ViewRotate extends ViewGroup {
	private ImageView left;// 左边的图片
	private ImageView right;// 右边的图片
	private ImageView top;// 上面的图片
	private ImageView bottom;// 下面的图片
	private ImageView center;// 中间的图片
	private int mScreenW;// 屏幕宽度
	private int mScreenH;// 屏幕高度
	private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
	private LinearInterpolator interpolator = new LinearInterpolator();

	public ViewRotate(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public ViewRotate(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ViewRotate(Context context) {
		super(context);
		init(context);
	}

	// 第一次绘制
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(mScreenW, mScreenH);
	}

	// 第二次绘制
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		center.layout(mScreenW / 2 - bitmaps.get(4).getWidth() / 2, mScreenH / 2 - bitmaps.get(4).getHeight() / 2,
				mScreenW / 2 + bitmaps.get(4).getWidth() / 2, mScreenH / 2 + bitmaps.get(4).getHeight() / 2);
		top.layout(mScreenW / 2 - bitmaps.get(2).getWidth() / 2, mScreenH / 2 - bitmaps.get(4).getHeight() / 2
				- bitmaps.get(2).getHeight() + bitmaps.get(2).getWidth() / 2 / 4, mScreenW / 2
				+ bitmaps.get(2).getWidth() / 2, mScreenH / 2 - bitmaps.get(4).getHeight() / 2
				+ bitmaps.get(2).getWidth() / 2 / 4);
		left.layout(mScreenW / 2 - bitmaps.get(0).getWidth() * 2 - bitmaps.get(0).getWidth() / 4, mScreenH / 2
				- bitmaps.get(0).getHeight() / 2, mScreenW / 2 - bitmaps.get(0).getWidth() - bitmaps.get(0).getWidth()
				/ 4, mScreenH / 2 + bitmaps.get(0).getHeight() / 2);
		right.layout(mScreenW / 2 + bitmaps.get(1).getWidth() + bitmaps.get(1).getHeight() / 4, mScreenH / 2
				- bitmaps.get(1).getHeight() / 2, mScreenW / 2 + bitmaps.get(1).getWidth() * 2
				+ bitmaps.get(1).getHeight() / 4, mScreenH / 2 + bitmaps.get(1).getHeight() / 2);
		bottom.layout(mScreenW / 2 - bitmaps.get(3).getWidth() / 2, mScreenH / 2 + bitmaps.get(4).getHeight() / 2
				- bitmaps.get(3).getWidth() / 2 / 4, mScreenW / 2 + bitmaps.get(3).getWidth() / 2, mScreenH / 2
				+ bitmaps.get(4).getHeight() / 2 + bitmaps.get(3).getHeight() - bitmaps.get(3).getWidth() / 2 / 4);
		playCenter();
		playTop();
		playBottom();
		playLeft();
		playRight();
	}

	private void init(Context context) {
		left = new ImageView(context);
		left.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.day));
		bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.day));
		right = new ImageView(context);
		right.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.night));
		bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.night));
		top = new ImageView(context);
		top.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.up));
		bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.up));
		bottom = new ImageView(context);
		bottom.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.down));
		bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.down));
		center = new ImageView(context);
		center.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.animation_battery));
		bitmaps.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.animation_battery));
		
		DisplayMetrics pm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(pm);
		mScreenH = pm.heightPixels;
		mScreenW = pm.widthPixels;
		this.addView(left);
		this.addView(right);
		this.addView(top);
		this.addView(bottom);
		this.addView(center);
	}

	private void playCenter() {
		AnimationSet animationSet = new AnimationSet(true);
		ScaleAnimation scaleSmall = new ScaleAnimation(1.0f, 0.6f, 1.0f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		ScaleAnimation scaleBig = new ScaleAnimation(1.0f, 5.0f, 1.0f, 5.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);

		scaleSmall.setDuration(2 * 100);
		scaleSmall.setStartOffset(2 * 1000);
		scaleSmall.setRepeatCount(2);
		scaleSmall.setFillEnabled(true);
		scaleSmall.setFillAfter(true);
		scaleSmall.setInterpolator(interpolator);

		scaleBig.setDuration(2 * 100);
		scaleBig.setInterpolator(interpolator);
		scaleBig.setStartOffset(2 * 1000);
		scaleBig.setRepeatCount(2);
		scaleBig.setFillEnabled(true);
		scaleBig.setFillAfter(true);
		animationSet.addAnimation(scaleBig);
		animationSet.addAnimation(scaleSmall);
		center.startAnimation(animationSet);
	}

	/**
	 * 上面的动画
	 */
	private void playTop() {
		RotateAnimation rotateAnimation = new RotateAnimation(0, 359, Animation.ABSOLUTE, mScreenW / 2 - top.getLeft(),
				Animation.ABSOLUTE, mScreenH / 2 - top.getTop());
		rotateAnimation.setDuration(10 * 1000);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setRepeatCount(-1);
		top.startAnimation(rotateAnimation);
	}

	/**
	 * 下面的动画
	 */
	private void playBottom() {
		RotateAnimation rotateBottom = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.ABSOLUTE, mScreenH / 2 - bottom.getTop());
		rotateBottom.setDuration(10 * 1000);
		rotateBottom.setInterpolator(interpolator);
		rotateBottom.setRepeatCount(-1);
		bottom.startAnimation(rotateBottom);
	}

	/**
	 * 右边的动画
	 */
	private void playRight() {
		// 混合动画
		AnimationSet animationSet = new AnimationSet(false);
		RotateAnimation rotateRight = new RotateAnimation(0, 359, Animation.ABSOLUTE, mScreenW / 2 - right.getLeft(),
				Animation.ABSOLUTE, (right.getBottom() - right.getTop()) / 2);
		RotateAnimation rotateSelf = new RotateAnimation(0, -359, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		// 播放时间
		rotateSelf.setDuration(10 * 1000);
		// 播放加速的模式
		rotateSelf.setInterpolator(interpolator);
		// 设置无限循环
		rotateSelf.setRepeatCount(-1);
		rotateRight.setDuration(10 * 1000);
		rotateRight.setRepeatCount(-1);
		rotateRight.setInterpolator(interpolator);
		animationSet.addAnimation(rotateSelf);
		animationSet.addAnimation(rotateRight);
		// 播放混合动画
		right.startAnimation(animationSet);
	}

	/**
	 * 左边的动画
	 */
	private void playLeft() {
		AnimationSet animationSet = new AnimationSet(false);
		RotateAnimation rotateLeft = new RotateAnimation(0, 359, Animation.ABSOLUTE, mScreenW / 2 - left.getLeft(),
				Animation.ABSOLUTE, (left.getBottom() - left.getTop()) / 2);
		rotateLeft.setDuration(10 * 1000);
		rotateLeft.setInterpolator(interpolator);
		rotateLeft.setRepeatCount(-1);
		RotateAnimation rotateSelf = new RotateAnimation(0, -359, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateSelf.setDuration(10 * 1000);
		rotateSelf.setRepeatCount(-1);
		rotateSelf.setInterpolator(interpolator);
		animationSet.addAnimation(rotateSelf);
		animationSet.addAnimation(rotateLeft);
		left.startAnimation(animationSet);
	}
}
