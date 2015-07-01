package com.myproject.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.myproject.R;

public class ClockView extends View implements Runnable {
	private Paint colorCirclePaint;// 表盘
	private Paint pointPaint;

	private Paint hourMarkPaint;// 小时标记
	private Paint minuteMarkPaint;// 分钟标记

	private Paint secondNeedlePaint;// 秒针
	private Paint minuteNeedlePaint;// 分针
	private Paint hourNeedlePaint;// 时针

	private Paint textPaint;
	private Paint timePaint;

	private float hourMarkLen;
	private float minuteMarkLen;

	private float clockCircle;
	private float radius;

	private float hourNeedleRadius;
	private float secondNeedleRadius;
	private float minuteNeedleRadius;

	private float cx;
	private float cy;

	private boolean running = false;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private int mSecond;

	private int clockColor;

	public ClockView(Context context) {
		super(context, null);
	}

	public ClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClockView);
		clockColor = array.getColor(R.styleable.ClockView_clockColor, Color.WHITE);
		array.recycle();

		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);// 防锯齿
		pointPaint.setStyle(Paint.Style.STROKE);// 让画出的图形是空心的

		colorCirclePaint = new Paint();
		colorCirclePaint.setTextAlign(Paint.Align.CENTER);//
		colorCirclePaint.setAntiAlias(true);
		colorCirclePaint.setStrokeWidth(clockCircle);// 设置“空心”的外框的宽度
		colorCirclePaint.setStyle(Paint.Style.STROKE);

		hourMarkPaint = new Paint();
		hourMarkPaint.setTextAlign(Paint.Align.CENTER);
		hourMarkPaint.setAntiAlias(true);
		hourMarkPaint.setStyle(Paint.Style.STROKE);

		minuteMarkPaint = new Paint();
		minuteMarkPaint.setTextAlign(Paint.Align.CENTER);
		minuteMarkPaint.setAntiAlias(true);
		minuteMarkPaint.setStyle(Paint.Style.STROKE);

		secondNeedlePaint = new Paint();
		secondNeedlePaint.setTextAlign(Paint.Align.CENTER);
		secondNeedlePaint.setAntiAlias(true);
		secondNeedlePaint.setStyle(Paint.Style.STROKE);

		minuteNeedlePaint = new Paint();
		minuteNeedlePaint.setTextAlign(Paint.Align.CENTER);
		minuteNeedlePaint.setAntiAlias(true);
		minuteNeedlePaint.setStyle(Paint.Style.STROKE);

		hourNeedlePaint = new Paint();
		hourNeedlePaint.setTextAlign(Paint.Align.CENTER);
		hourNeedlePaint.setAntiAlias(true);
		hourNeedlePaint.setStyle(Paint.Style.STROKE);

		textPaint = new Paint();
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setAntiAlias(true);

		timePaint = new Paint();
		timePaint.setTextAlign(Paint.Align.CENTER);
		timePaint.setAntiAlias(true);
		start();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	// 重置时间
	public void resetTime() {
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(new Date());
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond = c.get(Calendar.SECOND);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		pointPaint.setColor(clockColor);
		colorCirclePaint.setColor(clockColor);
		hourMarkPaint.setColor(clockColor);
		minuteMarkPaint.setColor(clockColor);
		secondNeedlePaint.setColor(clockColor);
		minuteNeedlePaint.setColor(clockColor);
		hourNeedlePaint.setColor(clockColor);
		textPaint.setColor(clockColor);
		timePaint.setColor(clockColor);

		// 原点
		cx = getWidth() / 2;
		cy = getHeight() / 2;

		// 不同屏幕
		int tmp = getWidth() <= getHeight() ? getWidth() : getHeight();

		radius = tmp / 2 - 2 * clockCircle;

		hourMarkLen = radius / 15;
		minuteMarkLen = radius / 30;
		clockCircle = radius / 60;

		pointPaint.setStrokeWidth(radius / 25);
		textPaint.setTextSize(radius / 8);
		timePaint.setTextSize(radius / 5);
		hourMarkPaint.setStrokeWidth(radius / 35);
		minuteMarkPaint.setStrokeWidth(radius / 70);
		secondNeedlePaint.setStrokeWidth(radius / 70);
		minuteNeedlePaint.setStrokeWidth(radius / 35);
		hourNeedlePaint.setStrokeWidth(radius / 20);

		hourNeedleRadius = radius - radius / 2;
		minuteNeedleRadius = radius - radius / 3;
		secondNeedleRadius = radius - radius / 5;
		drawClockPanel(canvas, radius);
		drawNeedle(canvas);
		drawTime(canvas);
	}

	// 表盘
	private void drawClockPanel(Canvas canvas, float radius) {

		// 画钟的外圈
		canvas.drawCircle(cx, cy, radius, colorCirclePaint);
		// 画钟的圆点
		canvas.drawCircle(cx, cy, radius / 60, pointPaint);
		int hourLen = 12;
		int minLen = 60;
		for (int index = 0; index < hourLen; index++) {
			drawMark(canvas, index, cx, cy, radius, radius - hourMarkLen,
					(2 * Math.PI / hourLen) * index + Math.PI / 2, hourMarkPaint, true);
		}
		for (int index = 0; index < minLen; index++) {
			drawMark(canvas, index, cx, cy, radius, radius - minuteMarkLen, (2 * Math.PI / minLen) * index + Math.PI
					/ 2, hourMarkPaint, false);

		}
	}

	private void drawNeedle(Canvas canvas) {
		double hourAngle = (2 * Math.PI / 12) * mHour + Math.PI / 2 + ((2 * Math.PI) / (12 * 60)) * mMinute
				+ ((2 * Math.PI) / (12 * 60 * 60)) * mSecond;
		double minuteAngle = (2 * Math.PI / (12 * 5)) * mMinute + Math.PI / 2 + ((2 * Math.PI) / (12 * 5 * 60))
				* mSecond;
		double secondAngle = (2 * Math.PI / 60) * mSecond + Math.PI / 2;
		drawNeedle(canvas, cx, cy, hourAngle, hourNeedleRadius, hourNeedlePaint);
		drawNeedle(canvas, cx, cy, minuteAngle, minuteNeedleRadius, minuteNeedlePaint);
		drawNeedle(canvas, cx, cy, secondAngle, secondNeedleRadius, secondNeedlePaint);
	}

	private void drawNeedle(Canvas canvas, float cx, float cy, double angle, float radius, Paint paint) {
		canvas.drawLine(cx, cy, (float) (cx - radius * Math.cos(angle)), (float) (cy - radius * Math.sin(angle)), paint);
	}

	// 时间标记
	private void drawMark(Canvas canvas, int index, float cx, float cy, float r1, float r2, double angle, Paint paint,
			boolean drawNumber) {
		float startX = (float) (cx - r2 * Math.cos(angle));
		float startY = (float) (cy - r2 * Math.sin(angle));
		float stopX = (float) (cx - r1 * Math.cos(angle));
		float stopY = (float) (cy - r1 * Math.sin(angle));
		float textSize = textPaint.getTextSize();
		float radiusText = r2 - textSize;
		if (index >= 3 && index <= 9) {
			radiusText = r2 - textSize / 3;
		}
		float textX = (float) (cx - radiusText * Math.cos(angle));
		float textY = (float) (cy - radiusText * Math.sin(angle));
		if (index == 3 || index == 9) {
			textY = textY + textSize / 4;
		}
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		if (drawNumber) {
			if (index == 0) {
				index = 12;
			}
			canvas.drawText(String.valueOf(index), textX, textY, textPaint);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void drawTime(Canvas canvas) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
		String dateText = dateFormat.format(new Date());
		String timeText = timeFormat.format(new Date());
		float textX = cx;
		float timeY = cy + radius / 2;
		float dateY = timeY + 1.5f * textPaint.getTextSize();
		canvas.drawText(dateText, textX, dateY, textPaint);
		canvas.drawText(timeText, textX, timeY, timePaint);
	}

	public int getClockColor() {
		return clockColor;
	}

	public void setClockColor(int clockColor) {
		this.clockColor = clockColor;
		this.postInvalidate();
	}

	// 开始
	public void start() {
		running = true;
		new Thread(this).start();
	}

	// 停止
	public void stop() {
		running = false;
	}

	public static Screen getScreenPix(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels);
	}

	public static class Screen {

		public int widthPixels;
		public int heightPixels;

		public Screen() {

		}

		public Screen(int widthPixels, int heightPixels) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}

	}

	@Override
	public void run() {
		while (running) {
			try {
				resetTime();
				postInvalidate();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
