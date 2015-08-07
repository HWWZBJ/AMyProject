package com.myproject.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by HEKL on 2015/8/7.
 * Used for 内容提供者
 */
public class MyProvider extends ContentProvider {
    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.app.provider", "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.app.provider", "table1/#", TABLE1_ITEM);
        uriMatcher.addURI("com.example.app.provider", "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.app.provider", "table2/#", TABLE2_ITEM);
    }

    //一个标准的内容URI:content://com.example.app.provider/table1
    //这就表示调用方期望访问的是com.example.app这个应用的table1表中的数据。除此之外，
    //我们还可以在这个内容URI的后面加上一个id,content://com.example.app.provider/table1/1
    //初始化内容提供器的时候调用。
    // 通常会在这里完成对数据库的创新和升级操作，
    // 返回true表示内容提供器初始化成功，返回false则表示失败。
    // 注意，只有当存在ContentResolver尝试访问我们程序中的数据时，内容提供器才会被初始化。
    @Override
    public boolean onCreate() {
        return false;
    }

    //从内容提供器中查询数据。
    // 使用uri参数来确定查询哪张表，
    // prjection参数用于确定查询哪些列，
    // selection和selectionArgs参数用于约束查询哪些行，
    // sortOrder参数用于对结果进行排序，查询的结果放在Cursor对象中返回
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                //查询table1表中的说有数据
                break;
            case TABLE1_ITEM:
                //查询table1表中的单条数据
                break;
            case TABLE2_DIR:
                //查询table2表中的单条数据
                break;
            case TABLE2_ITEM:
                //查询table2表中的单条数据
                break;
        }
        return null;
    }

    //根据传入的内容URI来返回相应的MIME类型
    @Override
    public String getType(Uri uri) {
        //MIME字符串
        //1、必须以vnd开头
        //2、以URI结尾，后面接android.cursor.dir/,以id结尾后面接android.cursor.item.
        //3、最后接上vdn.<authority>.<path>
        switch (uriMatcher.match(uri)) {
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.app.provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.app.provider.table2";
            default:
                break;
        }
        return null;
    }
    //向内容提供者中添加一条数据，
    // 使用uri参数来确定要添加到的表，
    // 待添加的数据保存在values参数中。
    // 添加完成后，返回一个用于表示这条新纪录的URI。
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    //从内容提供器中删除数据。
    // 使用uri参数来确定删除哪一张表中的数据，
    // selection和selectionArgs参数用于约束删除哪些行，
    // 被删除的行数将作为返回值返回
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    //更新内容提供器中已有的数据。
    // 使用uri参数来确定更新哪一张表中的数据，
    // 新数据保存在values参数中，
    // selection和selecionArgs参数用于约束更新哪些行，
    // 受影响的行数将作为返回值返回。
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
