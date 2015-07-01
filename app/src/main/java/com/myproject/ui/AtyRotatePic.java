package com.myproject.ui;

import android.os.Bundle;

import com.myproject.app.AtyNormal;
import com.myproject.view.ViewRotate;

public class AtyRotatePic extends AtyNormal {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ViewRotate(this));
	}
}
