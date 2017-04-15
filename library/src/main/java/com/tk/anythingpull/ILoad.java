package com.tk.anythingpull;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 上拉加载视图
 * </pre>
 */
public interface ILoad extends Action {
    /**
     * 准备加载，即 手指释放时可以触发加载
     */
    void preLoad();

    /**
     * 开始加载
     */
    void onLoadStart();

    /**
     * 结果回调
     *
     * @param success
     */
    void onLoadFinish(boolean success);
}
