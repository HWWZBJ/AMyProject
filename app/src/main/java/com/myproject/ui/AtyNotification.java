package com.myproject.ui;

import android.app.NotificationManager;
import android.os.Bundle;

import com.myproject.R;
import com.myproject.app.AtyNormal;

/**
 * Created by HEKL on 2015/8/7.
 * Used for
 */
public class AtyNotification extends AtyNormal {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_notification);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);//发送通知时候的id
    }
}
