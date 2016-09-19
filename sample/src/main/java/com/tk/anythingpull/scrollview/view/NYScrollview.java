package com.tk.anythingpull.scrollview.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.tk.library.implement.IPullable;


/**
 * Created by TK on 2016/7/21.
 */
public class NYScrollview extends NestedScrollView implements IPullable {
    public NYScrollview(Context context) {
        super(context);
    }

    public NYScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canPullDown(boolean hasHead) {
        return false;
    }

    @Override
    public boolean canPullUp(boolean hasFoot) {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight())) {
            return true;
        } else {
            return false;
        }
    }
}
