package com.tk.anythingpull;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/13
 *     desc   : 刷新和加载的公共功能
 * </pre>
 */
public interface Action {
    /**
     * 准备显示
     */
    void preShow();

    /**
     * 准备消失，回弹动画
     */
    void preDismiss();

    /**
     * 消失
     */
    void onDismiss();

    /**
     * 视图处于变化中
     *
     * @param touch
     * @param distance
     * @param status
     */

    void onPositionChange(boolean touch, int distance, @AnythingPullLayout.Status int status);
}
