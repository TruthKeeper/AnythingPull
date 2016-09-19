package com.tk.anythingpull.listview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.tk.library.implement.IPullable;

public class YNListView extends ListView implements IPullable {

    public YNListView(Context context) {
        super(context);
    }

    public YNListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YNListView(Context context, AttributeSet attrs, int defStyle) {
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
        return false;
    }
}
