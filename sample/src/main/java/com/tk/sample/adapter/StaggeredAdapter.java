package com.tk.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.sample.R;
import com.tk.sample.DataUtils;

/**
 * Created by TK on 2016/7/25.
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ItemHolder> {
    private String[] test;
    private Context context;

    public StaggeredAdapter(Context context) {
        this.context = context;
        test = DataUtils.initData();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.list_item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ((TextView) holder.itemView).setText(test[position]);
        ViewGroup.LayoutParams p = holder.itemView.getLayoutParams();
        p.height = 150 + 50 * (position % 3);
        holder.itemView.setLayoutParams(p);
    }

    @Override
    public int getItemCount() {
        return test.length;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
        }
    }

}
