package com.myproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleWaveView extends View implements Runnable {
	private Paint mSolidPaint;
	private Paint mLinePaint;
	private int waveColor = Color.argb(128, 255, 0, 0);
	private int waveColor2 = Color.argb(128, 180, 100, 0); // 颜色
	private float mWidth;
	private float mHeight;
	private float maxRadius = -1;// 圆半径
	private float floatRadius;// 变化的半径
	private int waveInterval = 50;// 圆环的宽度
	private float bottomMargin = 0;// 底部margin
	private float centerX;// 圆心x
	private float centerY;// 圆心y
	private boolean fillCircle = true;// 是否填充为实心圆环
	private boolean centerAlign = true;// 居中
	private volatile boolean started = false;

	public CircleWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CircleWaveView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public CircleWaveView(Context context) {
		super(context, null, 0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (maxRadius <= 0.0F) {
			return;
		}
		float radius = floatRadius % waveInterval;
		// float radius = 100;
		while (true) {
			int alpha = (int) (255.0F * (1.0F - radius / maxRadius));
			if (alpha <= 0) {
				break;
			}

			if (fillCircle) {
				mSolidPaint.setAlpha(alpha >> 2);
				canvas.drawCircle(centerX, centerY, radius - waveInterval / 2, mSolidPaint);
			}
			mLinePaint.setAlpha(alpha);
			canvas.drawCircle(centerX, centerY, radius, mLinePaint);
			radius += waveInterval;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (hasWindowFocus) {
			init();
		} else {
			stop();
		}
	}

	private void init() {
		mWidth = getWidth();
		mHeight = getHeight();
		mLinePaint = new Paint();
		mSolidPaint = new Paint();
		
		mLinePaint.setAntiAlias(true);
		mLinePaint.setStrokeWidth(1.0F);
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(waveColor);

		if (fillCircle) {
			mSolidPaint.setStyle(Paint.Style.STROKE);
			mSolidPaint.setStrokeWidth(waveInterval);
			mSolidPaint.setColor(waveColor2);
		}

		centerX = mWidth / 2.0F;
		if (centerAlign) {
			centerY = (mHeight / 2.0F);
		} else {
			centerY = mHeight - bottomMargin;
		}

		if (mWidth >= mHeight) {
			maxRadius = mHeight / 2.0F;
		} else {
			maxRadius = mWidth / 2.0F;
		}

		floatRadius = (maxRadius % waveInterval);

		start();
	}

	private void start() {
		if (!started) {
			started = true;
			new Thread(this).start();
		}
	}

	private void stop() {
		started = false;
	}

	@Override
	public void run() {
		while (started) {
			floatRadius = 4.0F + floatRadius;
			if (floatRadius > maxRadius) {
				floatRadius = (maxRadius % waveInterval);
			}
			postInvalidate();
			try {
				Thread.sleep(30L);
			} catch (InterruptedException localInterruptedException) {
				localInterruptedException.printStackTrace();
			}
		}
	}

	public void setMaxRadius(float maxRadius) {
		this.maxRadius = maxRadius;
	}

	public void setWaveColor(int waveColor) {
		this.waveColor = waveColor;
	}

	public void setWaveInterval(int waveInterval) {
		this.waveInterval = waveInterval;
	}

	public void setCenterAlign(boolean centerAlign) {
		this.centerAlign = centerAlign;
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stop();
	}
}
