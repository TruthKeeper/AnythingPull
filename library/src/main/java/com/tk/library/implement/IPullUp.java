package com.tk.library.implement;

/**
 * Created by TK on 2016/7/21.
 */
public interface IPullUp {
    /**
     * 开始滑动
     */
    void startPull();

    /**
     * 触摸时回调
     *
     * @param distance
     */
    void pull(float distance);

    /**
     * 滑动距离满足 放开加载 时回调
     */
    void releaseToLoad();

    /**
     * 滑动距离满足 放开变回原样 时回调
     */
    void releaseToInit();

    /**
     * 放开，触发加载时回调
     */
    void loading();

    /**
     * AnythingPull结束加载时回调
     *
     * @param isSuccess
     */
    void loadOver(boolean isSuccess);


}
