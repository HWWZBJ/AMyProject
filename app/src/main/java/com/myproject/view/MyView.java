package com.myproject.view;

import com.myproject.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	Paint mPaint;// 画笔，包含画几何图形、文本等的样式和颜色信息

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		// TypeArray是一个用来存放由context.obtainStyledAtrributes获得的属性的数组
		// 在使用完成后，一定要调用recycle的方法
		// 属性的名称是styleable中的名称+“_”+属性名称
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.MyView);
		int textColor = array
				.getColor(R.styleable.MyView_textColor, 0XFF00FF00);
		float textSize = array.getDimension(R.styleable.MyView_textSize, 36);// 提供默认值，放置为未指定
		mPaint.setColor(textColor);
		mPaint.setTextSize(textSize);
		array.recycle();// 一定要调用,否则这次的设定会对下次的使用造成影响
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setStyle(Style.FILL);
		canvas.drawRect(0, 0, 1000, 100, mPaint);// 绘制矩形
		mPaint.setColor(Color.BLUE);
		canvas.drawText("我是被画出来的", 10, 120, mPaint);

	}

}
