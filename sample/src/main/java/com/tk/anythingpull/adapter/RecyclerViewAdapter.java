package com.tk.anythingpull.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.anythingpull.R;

/**
 * Created by TK on 2016/7/25.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private String[] test;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        test = context.getResources().getStringArray(R.array.text_data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_test, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) holder.itemView).setText(test[position]);
    }

    @Override
    public int getItemCount() {
        return test.length;
    }

}
