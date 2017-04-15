package com.tk.anythingpull.adapter;

import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/15
 *     desc   : 抽屉式上拉加载
 * </pre>
 */
public class LoadDstAdapter extends ViewAdapter {

    public LoadDstAdapter(View view) {
        super(view);
    }

    @Override
    public void layout(int distance, AnythingPullLayout pullLayout) {
        int left = pullLayout.getPaddingLeft();
        int top = pullLayout.getBottom() - pullLayout.getPaddingBottom() - view.getMeasuredHeight();
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }

    @Override
    public int layoutLayer() {
        return -1;
    }
}
