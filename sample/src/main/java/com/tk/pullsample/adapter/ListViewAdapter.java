package com.tk.pullsample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tk.pullsample.R;

import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/16
 *     desc   : Adapter For ListView
 * </pre>
 */
public class ListViewAdapter extends BaseAdapter {
    private List<String> list;

    public ListViewAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        }
        ((TextView) convertView).setText(list.get(position));
        return convertView;
    }
}
