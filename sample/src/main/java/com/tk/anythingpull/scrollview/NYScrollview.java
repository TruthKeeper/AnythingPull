package com.tk.anythingpull.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class NYScrollview extends ScrollView implements Pullable {
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
