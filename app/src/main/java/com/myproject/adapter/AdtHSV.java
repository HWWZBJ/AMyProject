package com.myproject.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.myproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdtHSV extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;

	public AdtHSV(Context context) {
		super();
		this.list = new ArrayList<Map<String, Object>>();
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Map<String, Object> getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addObject(Map<String, Object> map) {
		list.add(map);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.adt_hsv, null);
		ImageView image = (ImageView) view.findViewById(R.id.move_image);
		Map<String, Object> map = getItem(position);
		image.setBackgroundResource((int) map.get("image"));
		return view;
	}
}
