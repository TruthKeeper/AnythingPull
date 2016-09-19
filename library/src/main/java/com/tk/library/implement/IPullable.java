package com.tk.library.implement;

/**
 * Created by TK on 2016/7/21.
 */
public interface IPullable {
    /**
     * 是否可以下拉刷新
     *
     * @param hasHead 携带刷新头
     * @return
     */
    boolean canPullDown(boolean hasHead);

    /**
     * 是否可以上拉加载
     *
     * @param hasFoot 携带加载底部
     * @return
     */
    boolean canPullUp(boolean hasFoot);
}
