package com.tk.library.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.tk.library.implement.IPullDown;
import com.tk.library.implement.IPullUp;
import com.tk.library.implement.IPullable;


/**
 * Created by TK on 2016/7/21.
 * Refreshed by TK on 2016/9/19.
 * 下拉刷新，上拉加载父容器
 * headerview   contentview   footview
 * TODO 2016/9/19 NestedScrollView ListView CANCEL事件待优化
 */
public class AnythingPullLayout extends RelativeLayout {
    //待机状态（无触摸）
    public static final int INIT = 0;
    //触摸状态（释放不触发刷新或者加载）
    public static final int PULLING = 1;
    //释放将触发下拉刷新
    public static final int TO_REFRESH = 2;
    //下拉刷新ing
    public static final int REFRESHING = 3;
    //释放将触发上拉加载
    public static final int TO_LOAD = 4;
    //上拉加载ing
    public static final int LOADING = 5;
    //支持的Mode
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
    private static final int DURATION = 350;
    //结果停留时间
    private static final int STAY_TIME = 1000;
    //// TODO: 2016/9/19  延迟时间
    private static final int DELAY_TIME = 500;
    //回弹下拉方向
    public static final int DIRECTION_DOWN = 0;
    //回弹上拉方向
    public static final int DIRECTION_UP = 1;
    //默认模式
    private int mode = FLEX_FLEX;
    //默认状态
    private int status = INIT;
    //头部
    private View headerView = null;
    private IPullDown iPullDown = null;
    //底部
    private View footView = null;
    private IPullUp iPullUp = null;
    //内容
    private View contentView = null;
    private IPullable iPullable = null;
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
    //动画锁
    private boolean animLock;
    private OnPullListener onPullListener;

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
                status = INIT;
                downY = ev.getY();
                moveY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (status == REFRESHING
                        || status == LOADING
                        || animLock) {
                    break;
                }
                if (iPullable.canPullDown(pulldownByMode())
                        && ev.getY() - downY > 0) {
                    pullUpY = 0;
                    if (ev.getY() > moveY) {
                        //正常下拉
                        pullDownY = pullDownY + (ev.getY() - moveY) / PRESSURE;
                        //偏移补正
                        downY += (ev.getY() - moveY - (ev.getY() - moveY) / PRESSURE);
                    } else {
                        //下拉过程中上拉，无阻力
                        if (pullDownY == 0) {
                            break;
                        }
                        pullDownY = pullDownY + (ev.getY() - moveY);
                    }
                    if (iPullDown != null) {
                        iPullDown.pull(pullDownY);
                        if (status == INIT) {
                            status = PULLING;
                            iPullDown.startPull();
                        }
                    }
                    if (mode != FLEX_FLEX
                            && mode != FLEX_LOAD
                            && mode != FLEX_NULL) {
                        if (pullDownY >= headerView.getMeasuredHeight()) {
                            if (status != TO_REFRESH) {
                                //just回调一次
                                status = TO_REFRESH;
                                iPullDown.releaseToRefresh();
                            }
                        } else {
                            if (status != PULLING) {
                                //just回调一次
                                status = PULLING;
                                iPullDown.releaseToInit();
                            }
                        }
                    }
                    if (ev.getY() > moveY
                            && (pullDownY + pullUpY) > 10) {
                        // 防止下拉过程中误触发长按事件和点击事件
                        //// TODO: 2016/7/29
                        cancelChildEvent(ev);
                    }
                    moveY = ev.getY();
                    requestLayout();
                    return true;
                } else if (iPullable.canPullUp(pullupByMode())
                        && ev.getY() - downY < 0) {
                    pullDownY = 0;
                    if (ev.getY() > moveY) {
                        //上拉过程中下拉，无阻力
                        if (pullUpY == 0) {
                            break;
                        }
                        pullUpY = pullUpY + (moveY - ev.getY());
                    } else {
                        //正常上拉
                        pullUpY = pullUpY + (moveY - ev.getY()) / PRESSURE;
                        //偏移补正
                        downY -= (moveY - ev.getY() - (moveY - ev.getY()) / PRESSURE);

                    }
                    if (iPullUp != null) {
                        iPullUp.pull(pullUpY);
                        if (status == INIT) {
                            status = PULLING;
                            iPullUp.startPull();
                        }
                    }
                    if (mode != REFRESH_FLEX
                            && mode != NULL_FLEX
                            && mode != FLEX_FLEX) {
                        if (pullUpY >= footView.getMeasuredHeight()) {
                            if (status != TO_LOAD) {
                                //just回调一次
                                status = TO_LOAD;
                                iPullUp.releaseToLoad();
                            }
                        } else {
                            if (status != PULLING) {
                                //just回调一次
                                status = PULLING;
                                iPullUp.releaseToInit();
                            }
                        }
                    }
                    if (ev.getY() < moveY
                            && (pullDownY + pullUpY) > 10) {
                        // 防止上拉过程中误触发长按事件和点击事件
                        //// TODO: 2016/7/29
                        cancelChildEvent(ev);
                    }
                    moveY = ev.getY();
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
                if ((pullDownY > 0 || pullUpY > 0)
                        && !animLock) {
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
        try {
            //// TODO: 2016/8/10  NestScrollview
            return super.dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
        }
        return true;
    }


    private void cancelChildEvent(MotionEvent ev) {
        ev.setAction(MotionEvent.ACTION_CANCEL);
        contentView.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (firstLayout) {
            //第一次加载子view
            int i = getChildCount();
            switch (i) {
                case 0:
                    throw new IllegalArgumentException("child count not standard");
                case 1:
                    if (getChildAt(0) instanceof IPullable) {
                        contentView = getChildAt(0);
                        iPullable = (IPullable) contentView;
                        calculMode((IPullable) contentView, 0);
                    } else {
                        throw new IllegalArgumentException("child not standard");
                    }
                    break;
                case 2:
                    if ((getChildAt(0) instanceof IPullDown)
                            && (getChildAt(1) instanceof IPullable)) {
                        //刷新头+body
                        headerView = getChildAt(0);
                        contentView = getChildAt(1);
                        iPullDown = (IPullDown) headerView;
                        iPullable = (IPullable) contentView;
                        calculMode((IPullable) contentView, 1);
                    } else if ((getChildAt(0) instanceof IPullable)
                            && (getChildAt(1) instanceof IPullUp)) {
                        //body+上拉底部
                        contentView = getChildAt(0);
                        footView = getChildAt(1);
                        iPullUp = (IPullUp) footView;
                        iPullable = (IPullable) contentView;
                        calculMode((IPullable) contentView, 0);
                    } else {
                        throw new IllegalArgumentException("child not standard");
                    }
                    break;
                case 3:
                    if ((getChildAt(0) instanceof IPullDown)
                            && (getChildAt(1) instanceof IPullable)
                            && (getChildAt(2) instanceof IPullUp)) {
                        //刷新头+body+上拉底部
                        headerView = getChildAt(0);
                        contentView = getChildAt(1);
                        footView = getChildAt(2);
                        iPullDown = (IPullDown) headerView;
                        iPullable = (IPullable) contentView;
                        iPullUp = (IPullUp) footView;
                        calculMode((IPullable) contentView, 1);
                    } else {
                        throw new IllegalArgumentException("child not standard");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("child count not standard");
            }
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
    private void startAnim(final float startF, float endF, final int direction, final int endS) {
        animator = new ValueAnimator().ofFloat(startF, endF).setDuration(DURATION);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (direction == AnythingPullLayout.DIRECTION_DOWN) {
                    pullDownY = (float) animation.getAnimatedValue();
                } else {
                    pullUpY = (float) animation.getAnimatedValue();
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
                status = endS;
                if (status == REFRESHING) {
                    iPullDown.refreshing();
                    if (onPullListener != null
                            && iPullDown != null) {
                        onPullListener.refreshing();
                    }
                } else if (status == LOADING) {
                    iPullUp.loading();
                    if (onPullListener != null
                            && iPullUp != null) {
                        onPullListener.loading();
                    }
                } else {
                    animLock = false;
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
     * 计算当前body view的触摸模式
     *
     * @param IPullable body
     * @param pullIndex 所处位置
     */
    private void calculMode(IPullable IPullable, int pullIndex) {
        if (pullIndex == 0) {
            if (getChildAt(1) == null) {
                //单一
                if (IPullable.canPullDown(false) && IPullable.canPullUp(false)) {
                    mode = FLEX_FLEX;
                } else if ((!IPullable.canPullDown(false)) && IPullable.canPullUp(false)) {
                    mode = NULL_FLEX;
                } else if (IPullable.canPullDown(false) && (!IPullable.canPullUp(false))) {
                    mode = FLEX_NULL;
                } else {
                    mode = NULL_NULL;
                }
            } else {
                //带底部view
                if (IPullable.canPullDown(false) && IPullable.canPullUp(true)) {
                    mode = FLEX_LOAD;
                } else if ((!IPullable.canPullDown(false)) && IPullable.canPullUp(true)) {
                    mode = NULL_LOAD;
                } else if (IPullable.canPullDown(false) && (!IPullable.canPullUp(true))) {
                    mode = FLEX_NULL;
                } else {
                    mode = NULL_NULL;
                }
            }
        } else if (pullIndex == 1) {
            if (getChildAt(2) == null) {
                //带顶部view
                if (IPullable.canPullDown(true) && IPullable.canPullUp(false)) {
                    mode = REFRESH_FLEX;
                } else if ((!IPullable.canPullDown(true)) && IPullable.canPullUp(false)) {
                    mode = NULL_FLEX;
                } else if (IPullable.canPullDown(true) && (!IPullable.canPullUp(false))) {
                    mode = REFRESH_NULL;
                } else {
                    mode = NULL_NULL;
                }
            } else {
                //三明治
                if (IPullable.canPullDown(true) && IPullable.canPullUp(true)) {
                    mode = REFRESH_LOAD;
                } else if ((!IPullable.canPullDown(true)) && IPullable.canPullUp(true)) {
                    mode = NULL_LOAD;
                } else if (IPullable.canPullDown(true) && (!IPullable.canPullUp(true))) {
                    mode = REFRESH_NULL;
                } else {
                    mode = NULL_NULL;
                }
            }
        }
    }

    /**
     * 通过mode计算是否支持下拉
     *
     * @return
     */
    private boolean pulldownByMode() {
        switch (mode) {
            case REFRESH_NULL:
                return true;
            case REFRESH_LOAD:
                return true;
            case FLEX_NULL:
                return true;
            case FLEX_FLEX:
                return true;
            case REFRESH_FLEX:
                return true;
            case FLEX_LOAD:
                return true;
            default:
                return false;
        }
    }

    /**
     * 通过mode计算是否支持上拉
     *
     * @return
     */
    private boolean pullupByMode() {
        switch (mode) {
            case NULL_LOAD:
                return true;
            case REFRESH_LOAD:
                return true;
            case NULL_FLEX:
                return true;
            case FLEX_FLEX:
                return true;
            case REFRESH_FLEX:
                return true;
            case FLEX_LOAD:
                return true;
            default:
                return false;
        }
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    public interface OnPullListener {
        void refreshing();

        void loading();
    }

    /**
     * 上拉加载完毕
     *
     * @param success
     */
    public void setLoadResult(boolean success) {
        if (iPullUp != null) {
            iPullUp.loadOver(success);
        }
        animLock = true;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim(footView.getMeasuredHeight(), 0, DIRECTION_UP, INIT);
            }
        }, STAY_TIME);
    }

    /**
     * 下拉刷新完毕
     *
     * @param success
     */
    public void setRefreshResult(boolean success) {
        if (iPullDown != null) {
            iPullDown.refreshOver(success);
        }
        animLock = true;
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnim(headerView.getMeasuredHeight(), 0, DIRECTION_DOWN, INIT);
            }
        }, STAY_TIME);
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
        }, DELAY_TIME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onPullListener = null;
        animLock = true;
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}
