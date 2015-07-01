package com.myproject.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.myproject.R;
import com.myproject.app.AtyNormal;
import com.myproject.interf.OnToggleStateChangedListener;
import com.myproject.view.ToggleView;

/**
 * Created by Administrator on 2015/7/1.
 * Used for
 */
public class AtyToggleView extends AtyNormal {
    private ToggleView mToggleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_toggleview_layout);
        mToggleView = (ToggleView) findViewById(R.id.toggleview);
        //        // 设置开关的状态为: 打开
//        mToggleView.setCurrentToggleState(false);
//
//        // 设置开关的背景id
//        mToggleView.setSwitchBackgroundID(R.drawable.switch_background);
//
//        // 设置开关的滑动块的背景
//        mToggleView.setSlideButtonBackgroundID(R.drawable.slide_button_background);

        // 设置开关状态监听
        mToggleView.setOnToggleStateChangedListener(new OnToggleStateChangedListener() {
            @Override
            public void OnToggleStateCHanged(boolean currentToggleState) {
                if (currentToggleState) {
                    Toast.makeText(AtyToggleView.this, "开关打开", 0).show();
                } else {
                    Toast.makeText(AtyToggleView.this, "开关关闭", 0).show();
                }
            }
        });
    }
}
