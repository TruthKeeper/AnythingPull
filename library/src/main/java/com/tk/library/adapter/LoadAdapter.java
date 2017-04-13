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
public abstract class LoadAdapter extends Adapter {
    protected ILoad iLoad;

    public LoadAdapter(@NonNull ILoad iLoad) {
        this.iLoad = iLoad;
    }
}
