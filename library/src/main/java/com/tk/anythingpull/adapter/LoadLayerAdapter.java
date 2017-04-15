package com.tk.anythingpull.adapter;

import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/15
 *     desc   : 布局层次上拉加载
 * </pre>
 */
public class LoadLayerAdapter extends ViewAdapter {

    public LoadLayerAdapter(View view) {
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
        int top = pullLayout.getBottom() - pullLayout.getPaddingBottom() - distance;
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }

    @Override
    public int layoutLayer() {
        return 1;
    }
}
