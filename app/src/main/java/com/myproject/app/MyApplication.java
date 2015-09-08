package com.myproject.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2015/8/14.
 * Used for
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
