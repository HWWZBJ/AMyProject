package com.myproject.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myproject.R;
import com.myproject.base.AtyNormal;
import com.myproject.bean.GsonTest;
import com.myproject.handler.SaxHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2015/8/12.
 * Used for
 */
public class AtyNetworkTest extends AtyNormal implements View.OnClickListener {
    private Button sendRequest, sendRequest2;
    private TextView responseText;
    public static final int SHOWW_RESPONSE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_networktest);
        sendRequest = (Button) findViewById(R.id.send_request);
        sendRequest2 = (Button) findViewById(R.id.send_request2);
        responseText = (TextView) findViewById(R.id.response);
        sendRequest.setOnClickListener(this);
        sendRequest2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_request:
                sendRequestWithHttpURLConnection();
                break;
            case R.id.send_request2:
                sendRequestWithHttpClient();
                break;
        }
    }

    /**
     * HttpURLConnection
     */
    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    //发送数据
//                    connection.setRequestMethod("POST");
//                    DataOutputStream out=new DataOutputStream(connection.getOutputStream());
//                    out.writeBytes("username=admin&password=123456");
                    //请求数据
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream is = connection.getInputStream();
                    //下面对获取到的输入流进行获取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder response = new StringBuilder();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = SHOWW_RESPONSE;
                    //将服务器返回的结果存放到Message中
                    message.obj = response.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }

    /**
     * HttpClient请求GET方式
     */
    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    //xml数据
//                    HttpGet httpGet = new HttpGet("http://192.168.16.200:8080/new/get_data.xml");
                    //json数据
                    HttpGet httpGet = new HttpGet("http://192.168.16.200:8080/new/get_data.json");
                    HttpResponse httpResponse = client.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //请求和响应都成功
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity, "utf-8");
                        //pull解析
//                      parseXMLWithPull(response);
                        //sax解析
//                        parseXMLWithSAX(response);
                        //JSONObject
//                        parseJSONWithJSONObject(response);
                        //Gson
                        parseJSONWithGSON(response);
//                        Message message = new Message();
//                        message.what = SHOWW_RESPONSE;
//                        //将服务器返回的结果存放到Message中
//                        message.obj = response.toString();
//                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOWW_RESPONSE:
                    String response = (String) msg.obj;
                    responseText.setText(response);
                    break;
            }
        }
    };

    /**
     * PUll解析xml
     *
     * @param xmlData
     */
    private void parseXMLWithPull(String xmlData) {
        try {
            //首先要获取到一个XmlPullParserFactory的实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //借助上面的实例得到XmlPullParser
            XmlPullParser xmlPullParser = factory.newPullParser();
            //将服务器返回的XML数据设置进去
            xmlPullParser.setInput(new StringReader(xmlData));
            //通过getEventType()可以得到当前的解析事件
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            //开始解析
            while (eventType != xmlPullParser.END_DOCUMENT) {
                //当前结点的名字
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("id".equals(nodeName)) {
                            //调用nextText方法获取结点内的具体内容
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个结点后打印
                    case XmlPullParser.END_TAG: {
                        if ("app".equals(nodeName)) {
                            Log.e(this.getLocalClassName(), "id is" + id);
                            Log.e(this.getLocalClassName(), "name is" + name);
                            Log.e(this.getLocalClassName(), "version is" + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SAX解析xml
     *
     * @param xmlData
     */
    private void parseXMLWithSAX(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            SaxHandler handler = new SaxHandler();
            //将SAXHandler 的实例设置到XMLReader中
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 原生解析json
     *
     * @param jsonData
     */
    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray array = new JSONArray(jsonData);
            int count = array.length();
            for (int i = 0; i < count; i++) {
                JSONObject obj = array.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String version = obj.getString("version");
                Log.e(this.getLocalClassName(), "id is" + id);
                Log.e(this.getLocalClassName(), "name is" + name);
                Log.e(this.getLocalClassName(), "version is" + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gson解析
     *
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        List<GsonTest> list = gson.fromJson(jsonData, new TypeToken<List<GsonTest>>() {
        }.getType());
        for (GsonTest test : list) {
            Log.e(this.getLocalClassName(), "id is" + test.getId());
            Log.e(this.getLocalClassName(), "name is" + test.getName());
            Log.e(this.getLocalClassName(), "version is" + test.getVersion());
        }
    }
}
