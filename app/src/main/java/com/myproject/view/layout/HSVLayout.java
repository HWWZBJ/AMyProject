package com.myproject.view.layout;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myproject.adapter.AdtHSV;
import com.myproject.app.AppContants;

public class HSVLayout extends LinearLayout {
	private AdtHSV mAdapter;
	private Context context;

	public HSVLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setAdapter(AdtHSV adtHSV) {
		this.mAdapter = adtHSV;
		for (int i = 0; i < adtHSV.getCount(); i++) {
			final Map<String, Object> map = adtHSV.getItem(i);
			View view = adtHSV.getView(i, null, null);
			view.setPadding(10, 0, 10, 0);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(context, "图片" + map.get("index"), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setAction(AppContants.UPDATE_IMAGE_ACTION);
					intent.putExtra("index", (Integer) map.get("index"));
					context.sendBroadcast(intent);
				}
			});
			this.setOrientation(HORIZONTAL);
			this.addView(view, new LinearLayout.LayoutParams(300, LayoutParams.WRAP_CONTENT));
		}
	}
}
