package com.myproject.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.myproject.R;
import com.myproject.base.AtyNormal;

public class AtyButterfly extends AtyNormal {
	private ImageView img_butterfly;
	private Handler mHandler;
	private AnimationDrawable butterfly;
	private float currentX, currentY;
	private float nextX, nextY;
	private TranslateAnimation anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_butterfly);
		img_butterfly = (ImageView) findViewById(R.id.img_butterfly);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 0) {
					if (currentX > 480) {
						currentX = nextX = 0;
					} else {
						nextX += 20;
					}
					nextY = currentY + (float) (Math.random() * 10 - 4);
					anim = new TranslateAnimation(currentX, nextX, currentY, nextY);
					currentX = nextX;
					currentY = nextY;
					anim.setDuration(1000);
					img_butterfly.startAnimation(anim);
				}
			}
		};

		butterfly = (AnimationDrawable) img_butterfly.getBackground();
		img_butterfly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				butterfly.start();
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						mHandler.sendEmptyMessage(0);
					}
				}, 0, 1000);
			}
		});
	}
}
