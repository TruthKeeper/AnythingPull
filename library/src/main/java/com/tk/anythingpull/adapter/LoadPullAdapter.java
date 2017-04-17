package com.tk.anythingpull.adapter;

import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/13
 *     desc   : 拉动式上拉加载
 * </pre>
 */
public class LoadPullAdapter extends ViewAdapter {

    public LoadPullAdapter(View view) {
        super(view);
    }

    @Override
    public void layout(int distance, AnythingPullLayout pullLayout) {
        int left = pullLayout.getPaddingLeft();
        int top = pullLayout.getMeasuredHeight() - pullLayout.getPaddingBottom() - distance;
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }
}
