package com.tk.anythingpull;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.tk.anythingpull.adapter.Adapter;
import com.tk.anythingpull.adapter.LoadPullAdapter;
import com.tk.anythingpull.adapter.RefreshPullAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 万能容器，下拉刷新，上拉加载
 * </pre>
 */
public class AnythingPullLayout extends ViewGroup {
    private static final String TAG = "AnythingPullLayout";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INIT,
            PRE_REFRESH, TO_REFRESH, REFRESH_ING, REFRESH_RESULT,
            PRE_LOAD, TO_LOAD, LOAD_ING, LOAD_RESULT})
    public @interface Status {
    }

    /**
     * 待机状态（无触摸）
     */
    public static final int INIT = 0;
    /**
     * 预刷新，下拉方向，释放不触发刷新
     */
    public static final int PRE_REFRESH = 1;
    /**
     * 释放将触发下拉刷新
     */
    public static final int TO_REFRESH = 2;
    /**
     * 下拉刷新ing
     */
    public static final int REFRESH_ING = 3;
    /**
     * 刷新结果停留
     */
    public static final int REFRESH_RESULT = 4;
    /**
     * 预加载，上拉方向，释放不触发加载
     */
    public static final int PRE_LOAD = 5;
    /**
     * 释放将触发上拉加载
     */
    public static final int TO_LOAD = 6;
    /**
     * 上拉加载ing
     */
    public static final int LOAD_ING = 7;
    /**
     * 加载结果停留
     */
    public static final int LOAD_RESULT = 8;

    private int mStatus;

    private boolean layoutInflate;

    private int refreshMode;
    private int loadMode;

    private IRefresh refreshView;
    private ILoad loadView;
    private int refreshViewHeight = 0;
    private int loadViewHeight = 0;

    private View contentView;
    /**
     * 下拉刷新的拖动距离
     */
    private int refreshDistance = 0;
    /**
     * 上拉加载的拖动距离
     */
    private int loadDistance = 0;
    /**
     * 刷新、加载视图适配器
     */
    private Adapter refreshAdapter;
    private Adapter loadAdapter;
    /**
     * 上次触点位置，会随MOVE事件补正
     */
    private int lastY;
    /**
     * 阻力
     */
    private float refreshResistance;
    private float loadResistance;
    /**
     * 回弹动画时长
     */
    private int refreshCloseDuring;
    private int loadCloseDuring;
    /**
     * 结果停留时长
     */
    private int refreshResultDuring;
    private int loadResultDuring;

    private boolean refreshFixed;
    private boolean loadFixed;

    private ValueAnimator animator;
    /**
     * 触摸阈值
     */
    private int touchSlop;

    private OnPullListener onPullListener;

    public AnythingPullLayout(Context context) {
        this(context, null);
    }

    public AnythingPullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnythingPullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnythingPullLayout);

        refreshMode = array.getInt(R.styleable.AnythingPullLayout_refresh_mode, 0);
        refreshResistance = array.getFloat(R.styleable.AnythingPullLayout_refresh_resistance, 1.6F);
        refreshCloseDuring = array.getInt(R.styleable.AnythingPullLayout_refresh_close_during, 300);
        refreshResultDuring = array.getInt(R.styleable.AnythingPullLayout_refresh_result_during, 750);
        refreshFixed = array.getBoolean(R.styleable.AnythingPullLayout_refresh_fixed, true);

        loadMode = array.getInt(R.styleable.AnythingPullLayout_load_mode, 0);
        loadResistance = array.getFloat(R.styleable.AnythingPullLayout_load_resistance, 1.6F);
        loadCloseDuring = array.getInt(R.styleable.AnythingPullLayout_load_close_during, 300);
        loadResultDuring = array.getInt(R.styleable.AnythingPullLayout_load_result_during, 750);
        loadFixed = array.getBoolean(R.styleable.AnythingPullLayout_load_fixed, false);

        array.recycle();
        touchSlop = Utils.dp2px(1.5F);
    }


    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutInflate();
        layoutSelf(false, 0);
    }

    private void layoutSelf(final boolean touch, final int mode) {
        if (refreshDistance < 0) {
            refreshDistance = 0;
        }
        if (loadDistance < 0) {
            loadDistance = 0;
        }
        MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
        int contentViewLeft = getPaddingLeft() + lp.leftMargin;
        int contentViewTop = getPaddingTop() + +lp.topMargin + (refreshDistance - loadDistance);
        int contentViewRight = contentViewLeft + contentView.getMeasuredWidth();
        int contentViewBottom = contentViewTop + contentView.getMeasuredHeight();

        contentView.layout(contentViewLeft, contentViewTop, contentViewRight, contentViewBottom);
        refreshAdapter.layout(refreshDistance, this);
        loadAdapter.layout(loadDistance, this);
        if (mode == -1) {
            //下拉刷新部分的位置监听回调
            if (mStatus == INIT) {
                //第一次执行
                mStatus = PRE_REFRESH;
                if (refreshView != null) {
                    refreshView.preShow();
                }
            }
            if (refreshView != null) {
                if (refreshViewHeight > 0 && refreshDistance >= refreshViewHeight) {
                    if (mStatus != TO_REFRESH && mStatus != REFRESH_ING && mStatus != REFRESH_RESULT) {
                        //执行一次，如果再刷新中就不重复执行刷新
                        mStatus = TO_REFRESH;
                        refreshView.preRefresh();
                    }
                } else if (mStatus != REFRESH_ING && mStatus != REFRESH_RESULT) {
                    mStatus = PRE_REFRESH;
                }
                refreshView.onPositionChange(touch, refreshDistance, mStatus);
            }

        } else if (mode == 1) {
            //上拉加载部分的位置监听回调
            if (mStatus == INIT) {
                //第一次执行
                mStatus = PRE_LOAD;
                if (loadView != null) {
                    loadView.preShow();
                }
            }
            if (loadView != null) {
                if (loadViewHeight > 0 && loadDistance >= loadViewHeight) {
                    if (mStatus != TO_LOAD && mStatus != LOAD_ING && mStatus != LOAD_RESULT) {
                        //执行一次
                        mStatus = TO_LOAD;
                        loadView.preLoad();
                    }
                } else if (mStatus != LOAD_ING && mStatus != LOAD_RESULT) {
                    mStatus = PRE_LOAD;
                }
                loadView.onPositionChange(touch, loadDistance, mStatus);
            }
        }
    }

    private void generateAnim(int from, int to, int during,
                              final int animDirection, final @Status int toStatus) {
        switch (mStatus) {
            case PRE_REFRESH:
                if (refreshView != null) {
                    refreshView.preDismiss();
                }
                break;
            case PRE_LOAD:
                if (loadView != null) {
                    loadView.preDismiss();
                }
                break;
            default:
                break;
        }

        animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(during);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (int) animation.getAnimatedValue();
                if (animDirection == 1) {
                    refreshDistance = i;
                } else if (animDirection == -1) {
                    loadDistance = i;
                }
                layoutSelf(false, -animDirection);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAnimEnd(animDirection, toStatus);
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

    private void onAnimEnd(final int animDirection, final @Status int toStatus) {
        switch (toStatus) {
            case INIT:
                if (animDirection == 1) {
                    if (refreshView != null) {
                        refreshView.onDismiss();
                        refreshView.onPositionChange(false, 0, toStatus);
                    }
                } else if (animDirection == -1) {
                    if (loadView != null) {
                        loadView.onDismiss();
                        loadView.onPositionChange(false, 0, toStatus);
                    }
                }
                break;
            case REFRESH_ING:
                if (refreshView != null && mStatus != REFRESH_ING && mStatus != REFRESH_RESULT) {
                    refreshView.onRefreshStart();
                    refreshView.onPositionChange(false, refreshViewHeight, toStatus);
                    if (onPullListener != null) {
                        onPullListener.onRefreshStart(this);
                    }
                }
                break;
            case LOAD_ING:
                if (loadView != null && mStatus != LOAD_ING && mStatus != LOAD_RESULT) {
                    loadView.onLoadStart();
                    loadView.onPositionChange(false, loadViewHeight, toStatus);
                    if (onPullListener != null) {
                        onPullListener.onLoadStart(this);
                    }
                }
                break;
            default:
                break;
        }
        mStatus = toStatus;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 3) {
            throw new IllegalStateException(TAG + " children count must <= 3 !");
        }
        if (count == 0) {
            throw new IllegalStateException(TAG + " no children !");
        }
    }

    private void layoutInflate() {
        if (layoutInflate) {
            return;
        }
        View child;
        View rV = null;
        View lV = null;
        for (int i = 0, count = getChildCount(); i < count; i++) {
            child = getChildAt(i);
            if (child instanceof IRefresh) {
                refreshView = (IRefresh) child;
                rV = child;
                refreshViewHeight = child.getMeasuredHeight();
            } else if (child instanceof ILoad) {
                loadView = (ILoad) child;
                lV = child;
                loadViewHeight = child.getMeasuredHeight();
            } else {
                contentView = child;
            }
        }
        switch (refreshMode) {
            case 0:
                refreshAdapter = rV == null ? empterRefresh : new RefreshPullAdapter(rV);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                refreshAdapter = empterRefresh;
                break;
        }
        switch (loadMode) {
            case 0:
                loadAdapter = lV == null ? empterLoad : new LoadPullAdapter(lV);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                loadAdapter = empterLoad;
                break;
        }
        //根据Mode重排布局

        layoutInflate = true;
    }

    /**
     * 重写分发规则，防止requestDisallowInterceptTouchEvent
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //取消之前的动画
                if (animator != null && animator.isRunning()) {
                    animator.cancel();
                }
                lastY = (int) event.getY();
                super.dispatchTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                //下拉上拉不允许同时存在
                if (event.getY() > lastY) {
                    //下拉
                    int dy;
                    if (ViewCompat.canScrollVertically(contentView, -1)) {
                        if (loadDistance > 0) {
                            if (loadFixed && (mStatus == LOAD_ING || mStatus == LOAD_RESULT)) {
                                break;
                            }
                            //上拉过程中下拉，默认无阻力
                            dy = (int) (lastY - event.getY());
                            lastY = (int) event.getY();
                            if (!checkLoad(dy)) {
                                return true;
                            }
                            int consumed = loadAdapter.pullConsumed(dy);
                            loadDistance += (dy - consumed);

                            layoutSelf(true, 1);
                            return true;
                        }
                        break;
                    } else {
                        //内容区域下拉到顶了
                        if (loadDistance > 0) {
                            //上拉加载部分尚在显示中
                            break;
                        }
                        //阻力
                        dy = (int) ((lastY - event.getY()) / refreshResistance);
                        lastY = (int) event.getY();
                        if (!checkRefresh(dy)) {
                            return true;
                        }
                        int consumed = refreshAdapter.pullConsumed(dy);
                        refreshDistance -= (dy - consumed);

                        layoutSelf(true, -1);
                        return true;
                    }
                } else {
                    //上拉
                    int dy;
                    if (ViewCompat.canScrollVertically(contentView, 1)) {
                        if (refreshDistance > 0) {
                            if (refreshFixed && (mStatus == REFRESH_ING || mStatus == REFRESH_RESULT)) {
                                break;
                            }
                            //下拉状态中上拉，默认无阻力
                            dy = (int) (lastY - event.getY());
                            lastY = (int) event.getY();
                            if (!checkRefresh(dy)) {
                                return true;
                            }
                            int consumed = refreshAdapter.pullConsumed(dy);
                            refreshDistance -= (dy - consumed);

                            layoutSelf(true, -1);
                            return true;
                        }

                        break;
                    } else {
                        //内容区域上拉到底了
                        if (refreshDistance > 0) {
                            //下拉刷新部分尚在显示中
                            break;
                        }
                        //阻力
                        dy = (int) ((lastY - event.getY()) / loadResistance);
                        lastY = (int) event.getY();
                        if (!checkLoad(dy)) {
                            return true;
                        }
                        int consumed = loadAdapter.pullConsumed(dy);
                        loadDistance += (dy - consumed);

                        layoutSelf(true, 1);
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (refreshDistance > 0 && (refreshDistance < refreshViewHeight || refreshViewHeight == 0)) {
                    //回弹<下拉刷新部分>
                    if (mStatus == REFRESH_ING || mStatus == REFRESH_RESULT) {
                        break;
                    }
                    generateAnim(refreshDistance, 0, refreshCloseDuring, 1, INIT);
                    break;
                }
                if (refreshDistance >= refreshViewHeight && refreshViewHeight > 0) {
                    //回弹至触发下拉刷新
                    generateAnim(refreshDistance, refreshViewHeight, refreshCloseDuring, 1, REFRESH_ING);
                    break;
                }
                if (loadDistance > 0 && (loadDistance < loadViewHeight || loadViewHeight == 0)) {
                    //回弹<上拉加载部分>
                    generateAnim(loadDistance, 0, loadCloseDuring, -1, INIT);
                    break;
                }
                if (loadDistance >= loadViewHeight && loadViewHeight > 0) {
                    //回弹至触发下拉刷新
                    generateAnim(loadDistance, loadViewHeight, loadCloseDuring, -1, LOAD_ING);
                    break;
                }
                break;
            default:
                break;
        }
        lastY = (int) event.getY();
        return super.dispatchTouchEvent(event);
    }

    /**
     * 刷新中的后续触摸判断
     *
     * @param dy
     * @return
     */
    private boolean checkRefresh(int dy) {
        if (Math.abs(dy) < touchSlop && (mStatus == REFRESH_ING || mStatus == REFRESH_RESULT)) {
            return false;
        }
        return true;
    }

    /**
     * 加载中的后续触摸判断
     *
     * @param dy
     * @return
     */
    private boolean checkLoad(int dy) {
        if (Math.abs(dy) < touchSlop && (mStatus == LOAD_ING || mStatus == LOAD_RESULT)) {
            return false;
        }
        return true;
    }

    /**
     * 下拉刷新回调
     * @param success
     */
    public void responseRefresh(boolean success) {
        if (refreshView != null && mStatus == REFRESH_ING) {
            refreshView.onRefreshFinish(success);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshView.preDismiss();
                    generateAnim(refreshDistance, 0, refreshCloseDuring, 1, INIT);
                }
            }, refreshResultDuring);
        }
    }

    /**
     * 上拉结果回调
     * @param success
     */
    public void responseload(boolean success) {
        if (loadView != null && mStatus == LOAD_ING) {
            loadView.onLoadFinish(success);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadView.preDismiss();
                    generateAnim(loadDistance, 0, loadCloseDuring, -1, INIT);
                }
            }, loadResultDuring);
        }
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    public interface OnPullListener {
        void onRefreshStart(final AnythingPullLayout pullLayout);

        void onLoadStart(final AnythingPullLayout pullLayout);
    }

    private static Adapter empterRefresh = new Adapter() {
        @Override
        public void layout(int distance, AnythingPullLayout pullLayout) {

        }
    };
    private static Adapter empterLoad = new Adapter() {
        @Override
        public void layout(int distance, AnythingPullLayout pullLayout) {

        }
    };
}
