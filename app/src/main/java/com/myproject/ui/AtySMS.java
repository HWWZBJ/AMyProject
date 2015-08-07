package com.myproject.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;

import com.myproject.R;
import com.myproject.app.AtyNormal;

/**
 * Created by HEKL on 2015/8/7.
 * Used for 消息的接收与发送
 */
public class AtySMS extends AtyNormal {
    private TextView sender,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_sms);
        sender= (TextView) findViewById(R.id.sender);
        content= (TextView) findViewById(R.id.content);
    }
    class MessageReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();
            Object[] pdus=(Object[])bundle.get("pdus");//提取短信消息
            SmsMessage[] messages=new SmsMessage[pdus.length];
            for (int i=0;i<messages.length;i++){
                messages[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
        }
    }
}
