package com.tk.anythingpull;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 下拉刷新视图，实现此接口
 * </pre>
 */
public interface IRefresh extends IAction {

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
