package com.myproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {
	// 图形类型
	int ss = 0;
	Paint paint = new Paint();
	Path path = new Path();

	// 构造方法
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// onDraw方法
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
		switch (ss) {
		// 园图形
		case 0:
			canvas.drawCircle(200, 200, 100, paint);
			break;
		// 画矩形
		case 1:
			canvas.drawRect(60, 90, 360, 300, paint);
			break;
		// 画三角
		case 2:
			path.moveTo(80, 100);
			path.lineTo(400, 250);
			path.lineTo(80, 400);
			path.close();
			canvas.drawPath(path, paint);
			break;

		default:
			break;
		}
	}

	public void changeStyle() {
		ss++;
		if (ss > 2) {
			ss = 0;
		}
	}

}
