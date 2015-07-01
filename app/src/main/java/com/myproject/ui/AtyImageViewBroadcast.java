package com.myproject.ui;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.myproject.R;
import com.myproject.adapter.AdtHSV;
import com.myproject.app.AppContants;
import com.myproject.view.layout.HSVLayout;

public class AtyImageViewBroadcast extends Activity implements ViewFactory {
	private ImageSwitcher mImageSwitcher;
	private HSVLayout mMoveLayout;
	private AdtHSV mAdapter;
	private Map<String, Object> map = null;
	private Integer[] images = { R.drawable.pre0, R.drawable.pre1, R.drawable.pre2, R.drawable.pre3, R.drawable.pre4,
			R.drawable.pre5, R.drawable.pre6, R.drawable.pre7, R.drawable.pre8, R.drawable.pre9, R.drawable.pre10 };
	private int mCount = 0;
	private BroadcastReceiver mBroadcastReceiver = null;
	private IntentFilter mFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_imageswitch);
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
		mImageSwitcher.setFactory(this);
		mMoveLayout = (HSVLayout) findViewById(R.id.move_layout);
		mAdapter = new AdtHSV(this);
		for (int i = 0; i < images.length; i++) {
			map = new HashMap<String, Object>();
			map.put("image", images[i]);
			map.put("index", (i + 1));
			mAdapter.addObject(map);
		}
		mMoveLayout.setAdapter(mAdapter);
		mImageSwitcher.setImageResource(images[mCount]);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mBroadcastReceiver == null) {
			mBroadcastReceiver = new UpdateImageReceiver();
		}
		registerReceiver(mBroadcastReceiver, getIntentFilter());
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	public View makeView() {
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setBackgroundColor(0xff000000);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		return imageView;
	}

	private IntentFilter getIntentFilter() {
		if (mFilter == null) {
			mFilter = new IntentFilter();
			mFilter.addAction(AppContants.UPDATE_IMAGE_ACTION);
		}
		return mFilter;
	}

	class UpdateImageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (AppContants.UPDATE_IMAGE_ACTION.equals(intent.getAction())) {
				int index = intent.getIntExtra("index", Integer.MAX_VALUE);
				mImageSwitcher.setImageResource(images[index - 1]);
				Toast.makeText(getApplicationContext(), index + "", Toast.LENGTH_SHORT);
			}
		}

	}
}
