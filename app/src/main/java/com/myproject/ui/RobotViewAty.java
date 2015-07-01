package com.myproject.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.myproject.R;
import com.myproject.view.RobotView;

public class RobotViewAty extends Activity {
	private RobotView robotView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myview_layout);
		
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.mylayout);
		robotView = new RobotView(RobotViewAty.this);
		frameLayout.addView(robotView);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			robotView.bitmapX = event.getX();
			robotView.bitmapY = event.getY();
			robotView.invalidate();
		}
		return super.onTouchEvent(event);

	}
}
