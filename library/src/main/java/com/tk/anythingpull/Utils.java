package com.tk.anythingpull;

import android.content.res.Resources;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/12
 *     desc   : 像素工具
 * </pre>
 */
public class Utils {

    /**
     * dp > px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(float dpValue) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dpValue * density);
    }

}
