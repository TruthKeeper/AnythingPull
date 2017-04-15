package com.tk.anythingpull.adapter;

import android.view.View;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 视图适配器
 * </pre>
 */
public abstract class ViewAdapter extends Adapter {
    protected View view;

    public ViewAdapter(View view) {
        this.view = view;
    }

    @Override
    public int layoutLayer() {
        return 0;
    }
}
