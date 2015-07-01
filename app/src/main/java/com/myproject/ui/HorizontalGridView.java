package com.myproject.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.myproject.R;
import com.myproject.adapter.AdtApp;
import com.myproject.adapter.AdtViewPager;
import com.myproject.view.PageControl;

/**
 * @author HEKL 实现横向滑动的GridView
 */
public class HorizontalGridView extends Activity implements OnItemClickListener {
	private static final float APP_PAGE_SIZE = 12.0f;
	private AdtViewPager mViewPager;
	private ViewPager mPager;
	LayoutInflater mInflater;
	private Map<Integer, GridView> map = null;
	private PageControl pageControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = getLayoutInflater();
		initView();
		mPager = (ViewPager) findViewById(R.id.myviewpager);
		mViewPager = new AdtViewPager(this, map);
		mPager.setAdapter(mViewPager);
		mPager.setOnPageChangeListener(new MyListener());
	}

	/**
	 * @Description:获取系统所有的应用程序，并根据APP_PAGE_SIZE生成相应的GridView页面
	 * @return void
	 * @throws
	 */
	private void initView() {
		final PackageManager manager = getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// get all apps
		final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
		// the total pages
		final int PageCount = (int) Math.ceil(apps.size() / APP_PAGE_SIZE);
		map = new HashMap<Integer, GridView>();
		for (int i = 0; i < PageCount; i++) {
			GridView appPage = new GridView(this);
			final AdtApp adtApp = new AdtApp(apps, this, i);
			appPage.setAdapter(adtApp);
			appPage.setNumColumns(4);
			appPage.setOnItemClickListener(adtApp);
			map.put(i, appPage);
		}
		ViewGroup main = (ViewGroup) mInflater.inflate(R.layout.horizontal_layout, null);
		ViewGroup group = (ViewGroup) main.findViewById(R.id.viewGroup);
		pageControl = new PageControl(this, (LinearLayout) group, PageCount);
		setContentView(main);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {

			pageControl.selectPage(arg0);
		}

	}
}
