package com.myproject.ui;

import com.myproject.R;
import com.myproject.view.ClockView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ClockViewAty extends Activity {
	private ClockView clockView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clockview_layout);
		clockView = (ClockView) findViewById(R.id.clockView);
	}

	public void OnStart(View v) {
		clockView.start();
	}

	public void OnStop(View v) {
		clockView.stop();
	}

	public void OnReset(View v) {
		clockView.resetTime();
	}
}
