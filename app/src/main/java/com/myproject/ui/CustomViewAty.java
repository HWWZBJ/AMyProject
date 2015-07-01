package com.myproject.ui;

import com.myproject.R;
import com.myproject.view.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public class CustomViewAty extends Activity {
	private CustomView cv;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				cv.changeStyle();
				cv.invalidate();
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customview_layout);
		cv = (CustomView) findViewById(R.id.customView);
		cv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		});

	}
}
