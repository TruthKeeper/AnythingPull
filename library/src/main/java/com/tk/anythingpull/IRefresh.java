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
