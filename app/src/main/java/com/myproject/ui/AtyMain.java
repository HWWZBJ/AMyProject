package com.myproject.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AtyMain extends ListActivity {
	private ArrayAdapter<ListCellData> adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ArrayAdapter<ListCellData>(this, android.R.layout.simple_list_item_1);
		setListAdapter(adapter);
		adapter.add(new ListCellData(this, "分类", new Intent(this, AtyExpandlistView.class)));
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
		adapter.add(new ListCellData(this, "文件的写入和读取", new Intent(this, AtyOutputStream.class)));
		adapter.add(new ListCellData(this, "查找联系人", new Intent(this, AtyContactTest.class)));
		adapter.add(new ListCellData(this, "接收和发送短信,拍照和选择照片", new Intent(this, AtySMS.class)));
		adapter.add(new ListCellData(this, "拍照和选择照片", new Intent(this, AtyTakePhoto.class)));
		adapter.add(new ListCellData(this, "播放音乐", new Intent(this, AtyMediaPlayer.class)));
		adapter.add(new ListCellData(this, "在子线程中更新UI", new Intent(this, AtyRunnble.class)));
		adapter.add(new ListCellData(this, "服务相关", new Intent(this, AtyService.class)));
		adapter.add(new ListCellData(this, "网络技术", new Intent(this, AtyTestHttp.class)));
		adapter.add(new ListCellData(this, "网络请求", new Intent(this, AtyNetworkTest.class)));
		adapter.add(new ListCellData(this, "定位服务", new Intent(this, AtyLocationTest.class)));
		adapter.add(new ListCellData(this, "百度地图", new Intent(this, AtyBaiduMap.class)));
		adapter.add(new ListCellData(this, "简易光照探测器", new Intent(this, AtyLigthSensor.class)));
		adapter.add(new ListCellData(this, "加速度传感器", new Intent(this, AtyAccelerometerSensor.class)));
		adapter.add(new ListCellData(this, "简易指南针", new Intent(this, AtyCompass.class)));
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
