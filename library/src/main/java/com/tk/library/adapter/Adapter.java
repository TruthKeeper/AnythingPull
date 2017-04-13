package com.tk.library.adapter;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 适配器，借鉴Nested的思想
 * </pre>
 */
public abstract class Adapter {
    public int currentY;

    public abstract void preShow();

    public int pullConsumed(int y) {
        return 0;
    }

    public abstract void layout(int contentLeft, int contentTop,
                                int contentRight, int contentBottom);

}
