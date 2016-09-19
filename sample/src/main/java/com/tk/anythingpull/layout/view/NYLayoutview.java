package com.tk.anythingpull.layout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.tk.library.implement.IPullable;


/**
 * Created by TK on 2016/7/21.
 */
public class NYLayoutview extends LinearLayout implements IPullable {
    public NYLayoutview(Context context) {
        this(context, null);
    }

    public NYLayoutview(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    public boolean canPullDown(boolean hasHead) {
        return false;
    }

    @Override
    public boolean canPullUp(boolean hasFoot) {
        return true;
    }
}
