package com.myproject.server;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.myproject.R;
import com.myproject.ui.AtyService;

/**
 * Created by HEKL on 2015/8/11.
 * Used for
 */
public class MyService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification notification = new Notification(R.drawable.ic_launcher, "Notification comes", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, AtyService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "This is title", "This is content", pendingIntent);
        startForeground(1, notification);
        Log.e("MyService", "onCreate executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService", "onStartCommand executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理具体的逻辑
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyService", "onDestroy executed");
    }

    public class DownloadBinder extends Binder {
        public void startDownload() {
            Log.e("MyService", "startDownload executed");
        }

        public int getProgress() {
            Log.e("MyService", "getProgress executed");
            return 0;
        }
    }
}
