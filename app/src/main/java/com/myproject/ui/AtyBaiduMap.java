package com.myproject.ui;

import android.os.Bundle;

import com.baidu.lbsapi.BMapManager;
import com.baidu.mapapi.map.MapView;
import com.myproject.R;
import com.myproject.base.AtyNormal;

/**
 * Created by Administrator on 2015/8/13.
 * Used for
 */
public class AtyBaiduMap extends AtyNormal {
    private BMapManager mapManager;
    private MapView mapView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapManager = new BMapManager(getApplicationContext());
        setContentView(R.layout.aty_baidumap);
        mapView = (MapView) findViewById(R.id.map_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
