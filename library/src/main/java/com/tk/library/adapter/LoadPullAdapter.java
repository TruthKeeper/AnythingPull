package com.tk.library.adapter;

import android.support.annotation.NonNull;

import com.tk.library.ILoad;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/13
 *     desc   : xxxx描述
 * </pre>
 */
public class LoadPullAdapter extends LoadAdapter {


    public LoadPullAdapter(@NonNull ILoad iLoad) {
        super(iLoad);
    }

    @Override
    public void preShow() {

    }

    @Override
    public int pullConsumed(int y) {
        iLoad.onScroll(y);
        return super.pullConsumed(y);
    }

    @Override
    public void layout(int contentLeft, int contentTop, int contentRight, int contentBottom) {

    }
}
