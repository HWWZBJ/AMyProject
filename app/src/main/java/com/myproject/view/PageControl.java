package com.myproject.view;

import com.myproject.R;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PageControl {
	private Context context;
	private LinearLayout layout;
	private int currentPage = 0;
	private int pageSize;
	private TextView[] textViews;
	private TextView textView;
	private int selectedImage = R.drawable.radio_sel;
	private int unselectedImage = R.drawable.radio;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public PageControl(Context context, LinearLayout layout, int pageSize) {
		super();
		this.context = context;
		this.layout = layout;
		this.pageSize = pageSize;
		initDot();
	}

	void initDot() {
		textViews = new TextView[pageSize];
		for (int i = 0; i < pageSize; i++) {
			textView = new TextView(context);
			textView.setLayoutParams(new LayoutParams(30, 30));
			textView.setPadding(2, 2, 2, 0);
			textViews[i] = textView;
			if (i == 0) {
				textViews[i].setBackgroundResource(R.drawable.radio_sel);
			} else {
				textViews[i].setBackgroundResource(R.drawable.radio);
			}
			layout.addView(textViews[i]);
		}
	}

	void turnToNextPage() {
		if (!isLast()) {
			currentPage++;
			selectPage(currentPage);
		}
	}

	void turnToPrePage() {
		if (!isFirst()) {
			currentPage--;
			selectPage(currentPage);
		}
	}

	boolean isFirst() {// 第一个
		return this.currentPage == 0;
	}

	boolean isLast() {// 最后一个
		return this.currentPage == pageSize;
	}

	public void selectPage(int current) {
		for (int i = 0; i < textViews.length; i++) {
			textViews[current].setBackgroundResource(R.drawable.radio_sel);
			if (current != i) {
				textViews[i].setBackgroundResource(R.drawable.radio);
			}
		}
	}
}
