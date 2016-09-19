package com.tk.anythingpull.listview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.tk.library.implement.IPullable;

public class PullListView extends ListView implements IPullable {
    public PullListView(Context context) {
        super(context);
    }
    public PullListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown(boolean hasHead) {
        if (getAdapter() == null || getAdapter().getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        }
        return getFirstVisiblePosition() == 0 && getChildAt(0) == null ? true : getChildAt(0).getTop() >= 0;
    }

    @Override
    public boolean canPullUp(boolean hasFoot) {
        if (getAdapter() == null || getAdapter().getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        }
        if (getLastVisiblePosition() == -1) {
            return true;
        }
        if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部
            View v = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
            if (v != null && v.getBottom() <= getMeasuredHeight()) {
                return true;
            }
        }
        return false;
    }
}
