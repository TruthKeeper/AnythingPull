package com.tk.anythingpull.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 拉动式下拉刷新
 * </pre>
 */
public class RefreshPullAdapter extends ViewAdapter {

    public RefreshPullAdapter(@NonNull View view) {
        super(view);
    }

    @Override
    public void layout(int distance, AnythingPullLayout pullLayout) {
        int left = pullLayout.getPaddingLeft();
        int top = pullLayout.getPaddingTop() - view.getMeasuredHeight() + distance;
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        view.layout(left, top, right, bottom);
    }
}
