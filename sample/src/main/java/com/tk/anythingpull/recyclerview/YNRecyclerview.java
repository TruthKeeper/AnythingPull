package com.tk.anythingpull.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class YNRecyclerview extends RecyclerView implements Pullable {
    public YNRecyclerview(Context context) {
        super(context);
    }

    public YNRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canPullDown() {
        // 没有item的时候也可以下拉刷新
        if (getAdapter() == null || getAdapter().getItemCount() == 0) {
            return true;
        }
        if (getLayoutManager() instanceof LinearLayoutManager) {
            int p = ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            return p == 0 || p == -1;
        }
        if (getLayoutManager() instanceof GridLayoutManager) {
            int p = ((GridLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            return p == 0 || p == -1;
        }
        if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) getLayoutManager();
            int columnCount = lm.getColumnCountForAccessibility(null, null);
            int positions[] = new int[columnCount];
            lm.findFirstCompletelyVisibleItemPositions(positions);
            if (positions[0] == -1 || positions[0] == 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
