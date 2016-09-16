package com.tk.anythingpull.scrollview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class PullScrollview extends NestedScrollView implements Pullable {
    public PullScrollview(Context context) {
        super(context);
    }

    public PullScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
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
