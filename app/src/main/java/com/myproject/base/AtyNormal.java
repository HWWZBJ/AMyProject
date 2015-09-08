package com.myproject.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.RelativeLayout;

/**
 * 框架Activity
 */
public class AtyNormal extends FragmentActivity {
    /**
     * 全局的LayoutInflater对象，已经完成初始化
     */
    public LayoutInflater mInflater;

    /**
     * 主内容布局.
     */
    protected RelativeLayout contentLayout = null;
    /**
     * 总布局.
     */
    public RelativeLayout ab_base = null;


    /**
     * 创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mInflater = LayoutInflater.from(this);
        //最外侧布局
        ab_base = new RelativeLayout(this);
        ab_base.setBackgroundColor(Color.rgb(255, 255, 255));
        //内容布局
        contentLayout = new RelativeLayout(this);
        contentLayout.setPadding(0, 0, 0, 0);
        //副标题栏

    }
}
