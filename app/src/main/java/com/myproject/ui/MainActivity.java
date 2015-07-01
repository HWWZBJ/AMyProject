package com.myproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	private ArrayAdapter<ListCellData> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ArrayAdapter<ListCellData>(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		adapter.add(new ListCellData(this, "拖动图标", new Intent(this, RobotViewAty.class)));
		adapter.add(new ListCellData(this, "自定义画图", new Intent(this, CustomViewAty.class)));
		adapter.add(new ListCellData(this, "自定义表", new Intent(this, ClockViewAty.class)));
		adapter.add(new ListCellData(this, "倒计时", new Intent(this, CountdownTimerAty.class)));
		adapter.add(new ListCellData(this, "展示应用横滑", new Intent(this, HorizontalGridView.class)));
		adapter.add(new ListCellData(this, "广播ImageSwitcher", new Intent(this, AtyImageViewBroadcast.class)));
		adapter.add(new ListCellData(this, "模糊效果", new Intent(this, AtyBlurEffect.class)));
		adapter.add(new ListCellData(this, "图标旋转", new Intent(this, AtyRotatePic.class)));
		adapter.add(new ListCellData(this, "圆形波纹", new Intent(this, AtyCircleWave.class)));
		adapter.add(new ListCellData(this, "蝴蝶动画", new Intent(this, AtyButterfly.class)));
		adapter.add(new ListCellData(this, "手电筒", new Intent(this, AtyButterfly.class)));
		adapter.add(new ListCellData(this, "自定义开关", new Intent(this, AtyToggleView.class)));

		// adapter.add(new ListCellData(this, "侧滑页", new Intent(this,
		// PageSlideAty.class)));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		ListCellData data = adapter.getItem(position);
		data.startActivity();
	}
}
