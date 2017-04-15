package com.tk.anythingpull.adapter;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 适配器，借鉴Nested的思想
 * </pre>
 */
public abstract class Adapter {
    /**
     * @param dy >0 上拉手势
     * @return 需要消耗的距离，返回dy的值就是全额消耗，内容视图不发生位移
     */
    public int pullConsumed(int dy) {
        return 0;
    }

    /**
     * 扩展view layout
     *
     * @param distance   >=0
     * @param pullLayout
     */
    public abstract void layout(int distance, AnythingPullLayout pullLayout);

}
