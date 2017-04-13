package com.tk.library.adapter;

import android.support.annotation.NonNull;

import com.tk.library.IRefresh;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : xxxx描述
 * </pre>
 */
public class RefreshPullAdapter extends RefreshAdapter {

    public RefreshPullAdapter(@NonNull IRefresh iRefresh) {
        super(iRefresh);
    }

    @Override
    public void preShow() {
        iRefresh.preShow();
    }

    @Override
    public int pullConsumed(int y) {
        iRefresh.onScroll(y);

        return 0;
    }

    @Override
    public void layout(int contentLeft, int contentTop, int contentRight, int contentBottom) {

    }


}
