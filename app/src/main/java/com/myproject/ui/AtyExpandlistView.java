package com.myproject.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.myproject.R;
import com.myproject.adapter.AdtExpandListView;
import com.myproject.base.AtyNormal;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/7.
 * Used for
 */
public class AtyExpandlistView extends AtyNormal {
    private ExpandableListView mlistView;
    private AdtExpandListView expandListViewAdapter;
    private ArrayList<String> mList;
    private ArrayList<String> mListChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_expandlistview);
        mList = new ArrayList<>();
        mListChild = new ArrayList<>();
        mList.add("手势相关");
        mListChild.add("手势获取");
        mlistView = (ExpandableListView) findViewById(R.id.expand_listView);
        expandListViewAdapter = new AdtExpandListView(this, mList, mListChild);
        mlistView.setAdapter(expandListViewAdapter);
        mlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:

                                Toast.makeText(getApplicationContext(), "sdf", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                }
                return true;
            }
        });
    }
}
