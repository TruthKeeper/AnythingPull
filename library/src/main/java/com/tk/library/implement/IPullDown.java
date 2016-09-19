package com.tk.library.implement;

/**
 * Created by TK on 2016/7/21.
 */
public interface IPullDown {
    /**
     * 触摸时回调
     *
     * @param distance
     */
    void pull(float distance);

    /**
     * 触摸时，滑动距离满足 放开刷新 时回调（弹性滑动时不回调）
     */
    void releaseToRefresh();

    /**
     * 触摸时，滑动距离满足 放开变回原样 时回调（弹性滑动时不回调）
     */
    void releaseToInit();

    /**
     * 放开，触发刷新时回调（弹性滑动时不回调）
     */
    void refreshing();

    /**
     * AnythingPull结束刷新时回调
     *
     * @param isSuccess
     */
    void refreshOver(boolean isSuccess);

    /**
     * 开始滑动,初始化
     */
    void startPull();

}
