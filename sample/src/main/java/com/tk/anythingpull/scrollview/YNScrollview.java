package com.tk.anythingpull.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 */
public class YNScrollview extends ScrollView implements Pullable {
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
