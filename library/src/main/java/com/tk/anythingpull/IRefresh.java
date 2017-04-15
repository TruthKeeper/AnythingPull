package com.tk.anythingpull;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 下拉刷新
 * </pre>
 */
public interface IRefresh extends Action {
    /**
     * 准备刷新，即 手指释放时可以触发刷新
     */
    void preRefresh();

    /**
     * 开始刷新
     */
    void onRefreshStart();

    /**
     * 结果回调
     *
     * @param success
     */
    void onRefreshFinish(boolean success);
}
