package com.myproject.interf;

/**
 * Created by HEKL on 2015/8/12.
 * Used for sendHttpRequest内部没有开启线程会是主线程被堵塞，又不能在其中写子线程，所以利用回调
 */
public interface HttpCallhackListener {
    void onFinish(String response);

    void onError(Exception e);
}
