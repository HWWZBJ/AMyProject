package com.myproject.view;

import com.myproject.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RobotView extends View {
	Paint mPaint;
	public float bitmapX;
	public float bitmapY;

	public RobotView(Context context) {
		super(context);
		bitmapX = 400;
		bitmapY = 500;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		canvas.drawBitmap(bitmap, bitmapX, bitmapY, mPaint);
		if (bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}
}
