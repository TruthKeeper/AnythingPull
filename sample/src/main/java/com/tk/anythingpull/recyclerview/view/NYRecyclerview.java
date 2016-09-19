package com.tk.anythingpull.recyclerview.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.tk.library.implement.IPullable;


/**
 * Created by TK on 2016/7/21.
 */
public class NYRecyclerview extends RecyclerView implements IPullable {
    private final int[] viewL = new int[2];
    private final int[] parentL = new int[2];

    public NYRecyclerview(Context context) {
        super(context);
    }

    public NYRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canPullDown(boolean hasHead) {
        return false;
    }

    @Override
    public boolean canPullUp(boolean hasFoot) {
        // 没有item的时候也可以上拉加载
        if (getAdapter() == null || getAdapter().getItemCount() == 0) {
            return true;
        }
        if (getLayoutManager() instanceof LinearLayoutManager) {
            int p = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            if (p == -1) {
                return true;
            }
            if (p < getAdapter().getItemCount() - 1) {
                return false;
            } else {
                getLocationInWindow(parentL);
                View v = findViewHolderForAdapterPosition(getAdapter().getItemCount() - 1).itemView;
                v.getLocationInWindow(viewL);
                return viewL[1] + v.getMeasuredHeight() <= parentL[1] + getMeasuredHeight();
            }
        }
        if (getLayoutManager() instanceof GridLayoutManager) {
            int p = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
            if (p == -1) {
                return true;
            }
            if (p < getAdapter().getItemCount() - 1) {
                return false;
            } else {
                getLocationInWindow(parentL);
                View v = findViewHolderForAdapterPosition(getAdapter().getItemCount() - 1).itemView;
                v.getLocationInWindow(viewL);
                return viewL[1] + v.getMeasuredHeight() <= parentL[1] + getMeasuredHeight();
            }
        }
        if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //// TODO: 2016/7/25
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) getLayoutManager();
            int columnCount = lm.getColumnCountForAccessibility(null, null);
            int positions[] = new int[columnCount];
            lm.findLastCompletelyVisibleItemPositions(positions);
            for (int i = 0; i < columnCount; i++) {
                if (positions[i] >= lm.getItemCount() - columnCount) {
                    //全滑到底才可以上拉
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
