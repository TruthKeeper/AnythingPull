package com.tk.anythingpull.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.tk.library.callback.Pullable;

public class NYListView extends ListView implements Pullable {

    public NYListView(Context context) {
        super(context);
    }

    public NYListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NYListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
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
