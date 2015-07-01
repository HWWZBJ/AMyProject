package com.myproject.adapter;

import java.util.List;
import java.util.Map;

import com.myproject.view.PageControl;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * @author HEKL Viewpager适配器
 */
public class AdtViewPager extends PagerAdapter {
	private List<GridView> array = null;
	private Map<Integer, GridView> map;
	private PageControl control;
	private Context mContext;

	public AdtViewPager(Context mContext, List<GridView> array) {
		super();
		this.array = array;
	}

	public AdtViewPager(Context mContext, Map<Integer, GridView> map) {
		super();
		this.map = map;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {

		return map.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(map.get(arg1));

		return map.get(arg1);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
		//
	}

}
