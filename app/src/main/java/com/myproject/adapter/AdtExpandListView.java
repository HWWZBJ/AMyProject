package com.myproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myproject.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/8.
 * Used for
 */
public class AdtExpandListView extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> mList;
    private ArrayList<String> mChildList;
    private LayoutInflater mInflater;

    public AdtExpandListView(Context context, ArrayList<String> mList, ArrayList<String> mChildList) {
        this.context = context;
        this.mList = mList;
        this.mChildList = mChildList;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        int count=0;
//        switch (groupPosition){
//            case 0:
//                count=2;
//                break;
//        }
        return mChildList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.adt_expandlistview_group_item, null);
            holder.fl_Group = (FrameLayout) convertView.findViewById(R.id.fl_group);
            holder.tvGroup = (TextView) convertView.findViewById(R.id.tv_groupname);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.fl_Group.setBackgroundColor(Color.rgb(0, 255, 0));
        } else {
            holder.fl_Group.setBackgroundColor(Color.rgb(255, 0, 0));
        }
        holder.tvGroup.setText(mList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = mInflater.inflate(R.layout.adt_expandlistview_child_item, null);
            holder.fl_Child = (FrameLayout) convertView.findViewById(R.id.fl_child);
            holder.tvChild = (TextView) convertView.findViewById(R.id.tv_childname);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.tvChild.setText(mChildList.get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView tvGroup;
        FrameLayout fl_Group;
    }

    class ChildViewHolder {
        TextView tvChild;
        FrameLayout fl_Child;
    }

}
