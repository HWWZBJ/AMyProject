package com.myproject.server;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Administrator on 2015/8/11.
 * Used for
 */
public class MyIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyIntentService() {
        super("MyIntentService");//调用父类的
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //打印当前线程的id
        Log.e("MyIntentService", "Thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyIntentService", "onDestroy executed");
    }
}
