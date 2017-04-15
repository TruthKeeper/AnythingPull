package com.tk.anythingpull.adapter;

import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/15
 *     desc   : 布局层次下拉刷新
 * </pre>
 */
public class RefreshLayerAdapter extends ViewAdapter {

    public RefreshLayerAdapter(View view) {
        super(view);
    }

    @Override
    public int pullConsumed(int dy) {
        //全额消耗
        return dy;
    }

    @Override
    public void layout(int distance, AnythingPullLayout pullLayout) {
        int left = pullLayout.getPaddingLeft();
        int top = pullLayout.getPaddingTop() - view.getMeasuredHeight() + distance;
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }

    @Override
    public int layoutLayer() {
        return 1;
    }
}
