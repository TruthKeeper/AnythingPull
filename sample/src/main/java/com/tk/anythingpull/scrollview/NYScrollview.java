package com.tk.anythingpull.scrollview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class NYScrollview extends NestedScrollView implements Pullable {
    public NYScrollview(Context context) {
        super(context);
    }

    public NYScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight())) {
            return true;
        } else {
            return false;
        }
    }

}
