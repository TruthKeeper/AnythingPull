package com.tk.library.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tk.library.callback.Pullable;


/**
 * Created by TK on 2016/7/21.
 * 下拉刷新，上拉加载父容器
 * headerview   contentview   footview
 * headerview footview 未设置且pullable为true 弹性滚动
 */
public class AnythingPullLayout extends RelativeLayout {
    //待机状态（包括释放不触发刷新或者加载）
    public static final int INIT = 0;
    //释放将触发下拉刷新
    public static final int TO_REFRESH = 1;
    //下拉刷新ing
    public static final int REFRESHING = 2;
    //释放将触发上拉加载
    public static final int TO_LOAD = 3;
    //上拉加载ing
    public static final int LOADING = 4;
    //下拉刷新
    public static final int REFRESH_NULL = 0;
    //上拉加载
    public static final int NULL_LOAD = 1;
    //下拉刷新，上拉加载
    public static final int REFRESH_LOAD = 2;
    //下拉弹性滚动
    public static final int FLEX_NULL = 3;
    //上拉弹性滚动
    public static final int NULL_FLEX = 4;
    //下拉弹性滚动，上拉弹性滚动
    public static final int FLEX_FLEX = 5;
    //下拉刷新，上拉弹性滚动
    public static final int REFRESH_FLEX = 6;
    //下拉弹性滚动，上拉加载
    public static final int FLEX_LOAD = 7;
    //什么都不干模式，就别用这个控件了啊喂ヽ(`Д´)ﾉ
    public static final int NULL_NULL = 8;
    //阻力
    private static final float PRESSURE = 3;
    //回滚时间
    private static final int DURATION = 250;
    //// TODO: 2016/7/24  自动加载延迟时间
    private static final int OFFSET_TIME = 750;
    //回调下拉方向
    public static final int DIRECTION_DOWN = 0;
    //回调上拉方向
    public static final int DIRECTION_UP = 1;
    //默认模式
    private int mode = FLEX_FLEX;
    //默认状态
    private int status = INIT;
    //头部
    private View headerView = null;
    //底部
    private View footView = null;
    //内容
    private View contentView = null;
    // 触摸下拉的距离>=0
    public float pullDownY = 0;
    // 触摸上拉的距离>=0,理论上pullDownY，pullUpY不可能同时大于0
    private float pullUpY = 0;
    //第一次layout设置模式
    private boolean firstLayout = true;
    //上次触点坐标
    private float downY;
    //上次滑动点坐标
    private float moveY;

    //回弹动画
    private ValueAnimator animator;
    //回调监听
    private OnStatusChangeListener onStatusChangeListener;
    //动画锁
    private boolean animLock;

    public AnythingPullLayout(Context context) {
        this(context, null);
    }

    public AnythingPullLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                moveY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (status == REFRESHING || status == LOADING) {
                    break;
                }
                if (((Pullable) contentView).canPullDown() && !animLock) {
                    if (ev.getY() > moveY) {
                        //正常下拉
                        pullDownY = pullDownY + (ev.getY() - moveY) / PRESSURE;
                    } else {
                        //下拉过程中上拉，无阻力
                        if (pullDownY == 0) {
                            break;
                        }
                        pullDownY = pullDownY + (ev.getY() - moveY);
                    }
                    moveY = ev.getY();
                    //接口回调
                    if (mode != FLEX_FLEX && mode != FLEX_LOAD && mode != FLEX_NULL
                            && pullDownY >= headerView.getMeasuredHeight()
                            ) {
                        status = TO_REFRESH;
                    } else {
                        status = INIT;
                    }
                    if (onStatusChangeListener != null) {
                        onStatusChangeListener.onChange(status, DIRECTION_DOWN, pullDownY);
                    }
                    requestLayout();
                    return true;
                } else if (((Pullable) contentView).canPullUp() && !animLock) {
                    if (ev.getY() > moveY) {
                        //上拉过程中下拉，无阻力
                        if (pullUpY == 0) {
                            break;
                        }
                        pullUpY = pullUpY + (moveY - ev.getY());
                    } else {
                        //正常上拉
                        pullUpY = pullUpY + (moveY - ev.getY()) / PRESSURE;
                    }
                    moveY = ev.getY();
                    //接口回调
                    if (mode != REFRESH_FLEX && mode != NULL_FLEX && mode != FLEX_FLEX
                            && pullUpY >= footView.getMeasuredHeight()
                            ) {
                        status = TO_LOAD;
                    } else {
                        status = INIT;
                    }
                    if (onStatusChangeListener != null) {
                        onStatusChangeListener.onChange(status, DIRECTION_UP, pullUpY);
                    }
                    requestLayout();
                    return true;
                } else {
                    moveY = ev.getY();
                    if (pullDownY != 0 || pullUpY != 0 && !animLock) {
                        pullDownY = 0;
                        pullUpY = 0;
                        requestLayout();
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (status == REFRESHING || status == LOADING) {
                    return super.dispatchTouchEvent(ev);
                }
                if ((pullDownY > 0 || pullUpY > 0) && !animLock) {
                    switch (mode) {
                        case FLEX_NULL:
                            startAnim(pullDownY, 0, DIRECTION_DOWN, INIT);
                            break;
                        case NULL_FLEX:
                            startAnim(pullUpY, 0, DIRECTION_UP, INIT);
                            break;
                        case FLEX_FLEX:
                            if (pullDownY > 0) {
                                startAnim(pullDownY, 0, DIRECTION_DOWN, INIT);
                            } else if (pullUpY > 0) {
                                startAnim(pullUpY, 0, DIRECTION_UP, INIT);
                            }
                            break;
                        case FLEX_LOAD:
                            if (pullDownY > 0) {
                                //执行回弹
                                startAnim(pullDownY, 0, DIRECTION_DOWN, INIT);
                            } else if (pullUpY >= footView.getMeasuredHeight()) {
                                //开始加载
                                startAnim(pullUpY, footView.getMeasuredHeight(), DIRECTION_UP, LOADING);
                            } else {
                                //执行回弹
                                startAnim(pullUpY, 0, 1, INIT);
                            }
                            break;
                        case REFRESH_FLEX:
                            if (pullDownY >= headerView.getMeasuredHeight()) {
                                //开始刷新
                                startAnim(pullDownY, headerView.getMeasuredHeight(), DIRECTION_DOWN, REFRESHING);
                            } else if (pullDownY > 0) {
                                //执行回弹
                                startAnim(pullDownY, 0, DIRECTION_DOWN, INIT);
                            } else if (pullUpY > 0) {
                                //执行回弹
                                startAnim(pullUpY, 0, DIRECTION_UP, INIT);
                            }
                            break;
                        case REFRESH_LOAD:
                            if (pullDownY >= headerView.getMeasuredHeight()) {
                                //开始刷新
                                startAnim(pullDownY, headerView.getMeasuredHeight(), DIRECTION_DOWN, REFRESHING);
                            } else if (pullDownY > 0) {
                                //执行回弹
                                startAnim(pullDownY, 0, DIRECTION_DOWN, INIT);
                            } else if (pullUpY >= footView.getMeasuredHeight()) {
                                //开始加载
                                startAnim(pullUpY, footView.getMeasuredHeight(), DIRECTION_UP, LOADING);
                            } else if (pullUpY > 0) {
                                //执行回弹
                                startAnim(pullUpY, 0, DIRECTION_UP, INIT);
                            }
                            break;
                        case REFRESH_NULL:
                            if (pullDownY >= headerView.getMeasuredHeight()) {
                                //开始刷新
                                startAnim(pullDownY, headerView.getMeasuredHeight(), DIRECTION_DOWN, REFRESHING);
                            } else if (pullDownY > 0) {
                                //执行回弹
                                startAnim(pullDownY, 0, DIRECTION_DOWN, -1);
                            }
                            break;
                        case NULL_LOAD:
                            if (pullUpY >= footView.getMeasuredHeight()) {
                                //开始加载
                                startAnim(pullUpY, footView.getMeasuredHeight(), DIRECTION_UP, LOADING);
                            } else if (pullUpY > 0) {
                                //执行回弹
                                startAnim(pullUpY, 0, DIRECTION_UP, INIT);
                            }
                            break;
                        case NULL_NULL:
                            //理论上不会走到这里
                            break;
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (firstLayout) {
            //第一次加载子view
            int i = getChildCount();
            if (i == 0) {
                throw new IllegalArgumentException("child count not allowed to empty");
            }
            if (i == 1 && getChildAt(0) instanceof Pullable) {
                calculMode((Pullable) getChildAt(0), 0);
                contentView = getChildAt(0);
            } else if (i == 2) {
                if (getChildAt(0) instanceof Pullable) {
                    calculMode((Pullable) getChildAt(0), 0);
                    contentView = getChildAt(0);
                    footView = getChildAt(1);
                } else {
                    calculMode((Pullable) getChildAt(1), 1);
                    headerView = getChildAt(0);
                    contentView = getChildAt(1);
                }
            } else if (i == 3 && getChildAt(1) instanceof Pullable) {
                calculMode((Pullable) getChildAt(1), 1);
                headerView = getChildAt(0);
                contentView = getChildAt(1);
                footView = getChildAt(2);
            } else {
                throw new IllegalArgumentException("child not standard");
            }

            ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
//            if (headerView == null) {
//                headerView = new Space(getContext());
//                headerView.setLayoutParams(p);
//            }
//            if (footView == null) {
//                footView = new Space(getContext());
//                footView.setLayoutParams(p);
//            }
            firstLayout = !firstLayout;
        }
        if (pullDownY < 0) {
            pullDownY = 0;
        }
        if (pullUpY < 0) {
            pullUpY = 0;
        }
        if (headerView != null) {
            headerView.layout(0,
                    (int) (pullDownY - pullUpY - headerView.getMeasuredHeight()),
                    headerView.getMeasuredWidth(),
                    (int) (pullDownY - pullUpY));
        }
        contentView.layout(0,
                (int) (pullDownY - pullUpY),
                contentView.getMeasuredWidth(),
                (int) (pullDownY - pullUpY + contentView.getMeasuredHeight()));
        if (footView != null) {
            footView.layout(0,
                    (int) (pullDownY - pullUpY) + bottom - top,
                    footView.getMeasuredWidth(),
                    (int) (pullDownY - pullUpY) + bottom - top
                            + footView.getMeasuredHeight());
        }
    }

    /**
     * 开始动画
     *
     * @param startF    开始的偏移量
     * @param endF      结束的偏移量
     * @param direction 方向0下拉，1上拉
     * @param endS      动画执行结束的后的status
     */
    private void startAnim(float startF, float endF, final int direction, final int endS) {
        animator = new ValueAnimator().ofFloat(startF, endF).setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (direction == AnythingPullLayout.DIRECTION_DOWN) {
                    pullDownY = (float) animation.getAnimatedValue();
                } else {
                    pullUpY = (float) animation.getAnimatedValue();
                }
                if (onStatusChangeListener != null && status != REFRESHING && status != LOADING) {
                    //刷新加载结束不回调
                    onStatusChangeListener.onChange(status, direction, direction == DIRECTION_DOWN ? pullDownY : pullUpY);
                }
                requestLayout();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animLock = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animLock = false;
                status = endS;
                if (onStatusChangeListener != null) {
                    onStatusChangeListener.onChange(status, direction, direction == DIRECTION_DOWN ? pullDownY : pullUpY);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * 计算当前view的触摸模式
     *
     * @param pullable
     * @param pullIndex
     */
    private void calculMode(Pullable pullable, int pullIndex) {
        if (pullIndex == 0) {
            if (getChildAt(1) == null) {
                //单一
                if (pullable.canPullDown() && pullable.canPullUp()) {
                    mode = FLEX_FLEX;
                } else if ((!pullable.canPullDown()) && pullable.canPullUp()) {
                    mode = NULL_FLEX;
                } else if (pullable.canPullDown() && (!pullable.canPullUp())) {
                    mode = FLEX_NULL;
                } else {
                    mode = NULL_NULL;
                }
            } else {
                //带底部view
                if (pullable.canPullDown() && pullable.canPullUp()) {
                    mode = FLEX_LOAD;
                } else if ((!pullable.canPullDown()) && pullable.canPullUp()) {
                    mode = NULL_LOAD;
                } else if (pullable.canPullDown() && (!pullable.canPullUp())) {
                    mode = FLEX_NULL;
                } else {
                    mode = NULL_NULL;
                }
            }
        } else if (pullIndex == 1) {
            if (getChildAt(2) == null) {
                //带顶部view
                if (pullable.canPullDown() && pullable.canPullUp()) {
                    mode = REFRESH_FLEX;
                } else if ((!pullable.canPullDown()) && pullable.canPullUp()) {
                    mode = NULL_FLEX;
                } else if (pullable.canPullDown() && (!pullable.canPullUp())) {
                    mode = REFRESH_NULL;
                } else {
                    mode = NULL_NULL;
                }
            } else {
                //三明治
                if (pullable.canPullDown() && pullable.canPullUp()) {
                    mode = REFRESH_LOAD;
                } else if ((!pullable.canPullDown()) && pullable.canPullUp()) {
                    mode = NULL_LOAD;
                } else if (pullable.canPullDown() && (!pullable.canPullUp())) {
                    mode = REFRESH_NULL;
                } else {
                    mode = NULL_NULL;
                }
            }
        }
    }

    public void setOnStatusChangeListener(OnStatusChangeListener onStatusChangeListener) {
        this.onStatusChangeListener = onStatusChangeListener;
    }

    public interface OnStatusChangeListener {
        void onChange(int status, int direction, float distance);
    }

    /**
     * 上拉加载完毕
     */
    public void setLoadResult() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim(footView.getMeasuredHeight(), 0, DIRECTION_UP, INIT);
            }
        }, OFFSET_TIME);
    }

    /**
     * 下拉刷新完毕
     */
    public void setRefreshResult() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim(headerView.getMeasuredHeight(), 0, DIRECTION_DOWN, INIT);
            }
        }, OFFSET_TIME);
    }

    /**
     * 自动下拉
     */
    public void autoRefresh() {
        animLock = true;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!(mode == REFRESH_FLEX ||
                        mode == REFRESH_LOAD ||
                        mode == REFRESH_NULL)) {
                    throw new IllegalArgumentException("mode not standard");
                }
                startAnim(0, headerView.getMeasuredHeight(), DIRECTION_DOWN, REFRESHING);
            }
        }, OFFSET_TIME);
    }
}
