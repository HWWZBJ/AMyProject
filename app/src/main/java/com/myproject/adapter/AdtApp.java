package com.myproject.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.R;

public class AdtApp extends BaseAdapter implements OnItemClickListener {
	private List<ResolveInfo> mList;// 定义一个list对象
	private Context context;// 上下文
	public static final int APP_PAGE_SIZE = 8;// 每一页装载数据的大小
	private PackageManager pm;
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public AdtApp(List<ResolveInfo> list, Context context, int page) {
		super();
		this.context = context;
		this.page = page;
		pm = context.getPackageManager();
		mList = new ArrayList<ResolveInfo>();
		// 根据当前页计算装载的应用，每页只装载16个
		int i = page * APP_PAGE_SIZE;// 当前页的起始位置
		int iEnd = i + APP_PAGE_SIZE;// 所有数据的结束位置
		while ((i < list.size()) && (i < iEnd)) {
			mList.add(list.get(i));
			i++;
		}
	}

	@Override
	public int getCount() {

		return mList.size();
	}

	@Override
	public Object getItem(int position) {

		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false);
		}
		ResolveInfo appInfo = mList.get(position);
		ImageView appIcon = (ImageView) convertView.findViewById(R.id.ivAppIcon);
		TextView appnName = (TextView) convertView.findViewById(R.id.tvAppName);
		appIcon.setImageDrawable(appInfo.loadIcon(pm));
		appnName.setText(appInfo.loadLabel(pm));
		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		int index = this.getPage() * APP_PAGE_SIZE + position;
		Toast.makeText(context, "lskjdlk", Toast.LENGTH_SHORT);
	}

}
