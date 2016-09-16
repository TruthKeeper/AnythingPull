package com.tk.anythingpull.scrollview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class YNScrollview extends NestedScrollView implements Pullable {
    public YNScrollview(Context context) {
        super(context);
    }

    public YNScrollview(Context context, AttributeSet attrs) {
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
        return false;
    }

}
