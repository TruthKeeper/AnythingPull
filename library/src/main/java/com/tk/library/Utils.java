package com.tk.library;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/12
 *     desc   : xxxx描述
 * </pre>
 */
public class Utils {

    public static boolean canRefresh(View view) {
        return ViewCompat.canScrollVertically(view, 1);
    }

}
