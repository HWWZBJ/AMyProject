package com.myproject.handler;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by HEKL on 2015/8/12.
 * Used for Sax解析
 */
public class SaxHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private float start;
    private float end;

    /**
     * 开始XML解析的时候调用
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
        start = System.currentTimeMillis();
    }

    /**
     * 在开始解析某个结点的时候调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException 在获取结点中的内容时，characters()方法可能会被调用多次，一些换行符也被当做内容解析出来，我们需要针对这种情况在代码中做好控制
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //记录当前结点名
        nodeName = localName;
    }

    /**
     * 在获取结点中内容的时候调用
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }

    /**
     * 在完成解析某个结点的时候调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("app".equals(localName)) {
            Log.e(this.getClass().toString(), "id is" + id.toString().trim());
            Log.e(this.getClass().toString(), "name is" + name.toString().trim());
            Log.e(this.getClass().toString(), "version is" + version.toString().trim());
            //最后要将StringBuilder清空掉
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    /**
     * 完成整个XML解析的时候调用
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        end = System.currentTimeMillis();
        Log.e(this.getClass().toString(), "time is" + String.valueOf((end - start)*1000));
    }
}
