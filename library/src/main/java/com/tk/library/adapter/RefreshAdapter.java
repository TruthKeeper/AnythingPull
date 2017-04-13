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
public abstract class RefreshAdapter extends Adapter {
    protected IRefresh iRefresh;

    public RefreshAdapter(@NonNull IRefresh iRefresh) {
        this.iRefresh = iRefresh;
    }
}
