package com.tk.anythingpull.adapter;

import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/15
 *     desc   : 抽屉式下拉刷新
 * </pre>
 */
public class RefreshDstAdapter extends ViewAdapter {

    public RefreshDstAdapter(View view) {
        super(view);
    }

    @Override
    public void layout(int distance, AnythingPullLayout pullLayout) {
        int left = pullLayout.getPaddingLeft();
        int top = pullLayout.getPaddingTop();
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }

    @Override
    public int getLayer() {
        return -1;
    }
}
