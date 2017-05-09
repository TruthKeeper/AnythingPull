package com.tk.pullsample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.pullsample.OnItemClickListener;
import com.tk.pullsample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/17
 *     desc   : 网格
 * </pre>
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ItemHolder> {
    private List<String> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public GridAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ((TextView) holder.itemView).setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
