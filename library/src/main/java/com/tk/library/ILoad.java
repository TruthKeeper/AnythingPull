package com.tk.library;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 上拉加载视图
 * </pre>
 */
public interface ILoad {
    /**
     * 准备显示
     */
    void preShow();

    /**
     * 触摸时的MOVE事件
     *
     * @param dy
     */
    void onScroll(int dy);

    /**
     * 手指释放
     */
    void onRelease();

    /**
     * 结果回调
     *
     * @param isSuccess
     */
    void onFinish(boolean isSuccess);

}
