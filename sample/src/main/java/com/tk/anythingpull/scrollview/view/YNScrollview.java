package com.tk.anythingpull.scrollview.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.tk.library.implement.IPullable;


/**
 * Created by TK on 2016/7/21.
 */
public class YNScrollview extends NestedScrollView implements IPullable {
    public YNScrollview(Context context) {
        super(context);
    }

    public YNScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canPullDown(boolean hasHead) {
        if (getScrollY() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp(boolean hasFoot) {
        return false;
    }
}
