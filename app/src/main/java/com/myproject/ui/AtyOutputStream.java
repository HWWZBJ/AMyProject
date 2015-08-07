package com.myproject.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.myproject.R;
import com.myproject.app.AtyNormal;
import com.myproject.db.MyDatabaseHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by HEKL on 2015/8/6.
 * Used for
 */
public class AtyOutputStream extends AtyNormal {
    private EditText editText;
    private MyDatabaseHelper dbHelper;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_outputstream);
        editText = (EditText) findViewById(R.id.editText);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        String editStr = show();
        if (!TextUtils.isEmpty(editStr)) {
            editText.setText(editStr);
            editText.setSelection(editStr.length());
            Toast.makeText(AtyOutputStream.this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                Toast.makeText(AtyOutputStream.this, str, Toast.LENGTH_SHORT).show();
                save(str);
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        findViewById(R.id.btn_sql).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        findViewById(R.id.btn_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.ic_launcher, "This is ticker text", System.currentTimeMillis());
                Intent intent=new Intent(getApplicationContext(),AtyNotification.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                notification.setLatestEventInfo(getApplicationContext(), "This is content title", "This is content text", pi);
                //音频
                //Uri soundUri=Uri.fromFile(new File("/sysem/media/audio/ringtones"));
                //notification.sound=soundUri;
//                long[] vibrates={0,1000,1000,1000};
//                notification.vibrate=vibrates;
//                notification.ledARGB= Color.GREEN;//LED灯的颜色
//                notification.ledOnMS=1000;//亮的时长
//                notification.ledOffMS=1000;//暗的时长
//                notification.flags=Notification.FLAG_SHOW_LIGHTS;
                notification.defaults=Notification.DEFAULT_ALL;
                manager.notify(1, notification);
            }
        });

    }

    /**
     * 展示存储到内存的文件
     *
     * @return content
     */
    public String show() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("hhh");
            reader = new BufferedReader(new InputStreamReader(in));

            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            Toast.makeText(AtyOutputStream.this, content.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    /**
     * 将文件存储到
     *
     * @param inputText
     */
    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        File textsDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "huoweiwei");//File.separator分隔符
        if (!textsDir.exists()) {
            textsDir.mkdir();
        }
        try {
//            File f=new File(textsDir,"huo.txt");
//            Toast.makeText(AtyOutputStream.this,f.getAbsolutePath(),Toast.LENGTH_LONG).show();
//            out=new FileOutputStream(f);
            out = openFileOutput("hhh", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
