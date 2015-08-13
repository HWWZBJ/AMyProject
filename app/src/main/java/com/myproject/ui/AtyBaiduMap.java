package com.myproject.ui;

import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.myproject.R;
import com.myproject.app.AtyNormal;

/**
 * Created by Administrator on 2015/8/13.
 * Used for
 */
public class AtyBaiduMap extends AtyNormal {
    private MapView mapView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.aty_baidumap);
        mapView = (MapView) findViewById(R.id.map_view);
        GeoPoint point=new GeoPoint((int)(39.915*1E6),(int)(116.404*1E6));
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
