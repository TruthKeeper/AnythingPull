package com.tk.pullsample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tk.anythingpull.Utils;
import com.tk.pullsample.OnItemClickListener;
import com.tk.pullsample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/17
 *     desc   : 瀑布流
 * </pre>
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ItemHolder> {
    private List<String> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public StaggeredAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        ((TextView) holder.itemView).setText(list.get(position));
        ViewGroup.LayoutParams p = holder.itemView.getLayoutParams();
        p.height = Utils.dp2px(50) + 50 * (position % 3);
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
