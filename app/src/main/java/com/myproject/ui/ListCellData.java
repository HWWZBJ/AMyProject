package com.myproject.ui;

import android.content.Context;
import android.content.Intent;

public class ListCellData {
	private Context context;
	private String controlName = null;
	private Intent intent = null;

	public ListCellData(Context context, String controlName, Intent intent) {
		super();
		this.context = context;
		this.controlName = controlName;
		this.intent = intent;
	}

	public Context getContext() {
		return context;
	}

	public String getControlName() {
		return controlName;
	}

	public Intent getIntent() {
		return intent;
	}

	public void startActivity() {
		getContext().startActivity(intent);
	}

	@Override
	public String toString() {
		return getControlName();
	}

}
