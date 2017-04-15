package com.tk.sample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tk.sample.R;
import com.tk.sample.DataUtils;

/**
 * Created by TK on 2016/7/25.
 */
public class ListViewAdapter extends BaseAdapter {
    private String[] data;

    public ListViewAdapter() {
        data = DataUtils.initData();
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_test, parent, false);
        }
        ((TextView) convertView).setText(data[position]);
        return convertView;
    }
}
