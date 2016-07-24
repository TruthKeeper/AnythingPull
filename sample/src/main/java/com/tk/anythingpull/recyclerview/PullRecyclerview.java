package com.tk.anythingpull.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class PullRecyclerview extends RecyclerView implements Pullable {
    public PullRecyclerview(Context context) {
        super(context);
    }

    public PullRecyclerview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        return true;
    }

    @Override
    public boolean canPullUp() {
        return true;
    }
}
