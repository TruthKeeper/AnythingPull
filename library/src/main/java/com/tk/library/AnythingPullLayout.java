package com.tk.library;

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

import com.tk.library.adapter.Adapter;
import com.tk.library.adapter.RefreshPullAdapter;

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
    @IntDef({INIT, DRAG_REFRESH, TO_REFRESH, REFRESH_ING, DRAG_LOAD, TO_LOAD, LOAD_ING})
    public @interface Status {
    }

    /**
     * 待机状态（无触摸）
     */
    public static final int INIT = 0;
    /**
     * 拖拽状态，下拉方向，释放不触发刷新
     */
    public static final int DRAG_REFRESH = 1;
    /**
     * 释放将触发下拉刷新
     */
    public static final int TO_REFRESH = 2;
    /**
     * 下拉刷新ing
     */
    public static final int REFRESH_ING = 3;
    /**
     * 拖拽状态，上拉方向，释放不触发加载
     */
    public static final int DRAG_LOAD = 4;
    /**
     * 释放将触发上拉加载
     */
    public static final int TO_LOAD = 5;
    /**
     * 上拉加载ing
     */
    public static final int LOAD_ING = 6;

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

    private Adapter refreshAdapter;
    private Adapter loadAdapter;

    private int downY;
    private int lastY;
    private float refreshResistance;
    private float loadResistance;

    private ValueAnimator animator;

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
        array.getInt(R.styleable.AnythingPullLayout_refresh_during, 0);
        array.getInt(R.styleable.AnythingPullLayout_refresh_over_during, 0);

        loadMode = array.getInt(R.styleable.AnythingPullLayout_load_mode, 0);
        loadResistance = array.getFloat(R.styleable.AnythingPullLayout_load_resistance, 1.6F);
        array.getInt(R.styleable.AnythingPullLayout_load_during, 0);
        array.getInt(R.styleable.AnythingPullLayout_load_over_during, 0);

        array.recycle();

    }

    private void generateAnim(int from, int to, int during,
                              final boolean isRefresh, final @Status int toStatus) {
        animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(during);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (int) animation.getAnimatedValue();
                if (isRefresh) {
                    refreshDistance = i;
                } else {
                    loadDistance = i;
                }
                layoutSelf();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = toStatus;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
        layoutSelf();
    }

    private void layoutSelf() {
        MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
        int contentViewLeft = getPaddingLeft() + lp.leftMargin;
        int contentViewTop = getPaddingTop() + +lp.topMargin + (refreshDistance - loadDistance);
        int contentViewRight = contentViewLeft + contentView.getMeasuredWidth();
        int contentViewBottom = contentViewTop + contentView.getMeasuredHeight();

        contentView.layout(contentViewLeft, contentViewTop, contentViewRight, contentViewBottom);
        refreshAdapter.layout(contentViewLeft, contentViewTop, contentViewRight, contentViewBottom);
        loadAdapter.layout(contentViewLeft, contentViewTop, contentViewRight, contentViewBottom);
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
        for (int i = 0, count = getChildCount(); i < count; i++) {
            child = getChildAt(i);
            if (child instanceof IRefresh) {
                refreshView = (IRefresh) child;
                refreshViewHeight = child.getMeasuredHeight();
            } else if (child instanceof ILoad) {
                loadView = (ILoad) child;
                loadViewHeight = child.getMeasuredHeight();
            } else {
                contentView = child;
            }
        }
        switch (refreshMode) {
            case 0:
                refreshAdapter = refreshView == null ? empterRefresh : new RefreshPullAdapter(refreshView);
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
                loadAdapter = loadView == null ? empterLoad : empterLoad;
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
                downY = (int) event.getY();
                lastY = (int) event.getY();
                super.dispatchTouchEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() > lastY) {
                    //下拉
                    int dy;
                    if (ViewCompat.canScrollVertically(contentView, -1)) {
                        if (loadDistance > 0 && (mStatus == DRAG_LOAD || mStatus == TO_LOAD)) {
                            //上拉过程中下拉，默认无阻尼
                            dy = (int) (lastY - event.getY());
                            lastY = (int) event.getY();
                            int consumed = loadAdapter.pullConsumed(dy);
                            loadDistance += (dy - consumed);

                            if (loadDistance < 0) {
                                loadDistance = 0;
                                mStatus = DRAG_LOAD;
                            } else if (loadViewHeight > 0 && loadDistance > loadViewHeight) {
                                mStatus = TO_LOAD;
                            } else {
                                mStatus = DRAG_LOAD;
                            }
                            layoutSelf();
                            return true;
                        }
                        lastY = (int) event.getY();
                        break;
                    } else {
                        //内容区域下拉到顶了
                        dy = (int) ((lastY - event.getY()) / refreshResistance);
                        lastY = (int) event.getY();

                        if (mStatus == INIT) {
                            refreshAdapter.preShow();
                            mStatus = DRAG_REFRESH;
                        }
                        int consumed = refreshAdapter.pullConsumed(dy);
                        refreshDistance -= (dy - consumed);
                        if (refreshViewHeight > 0 && refreshDistance > refreshViewHeight) {
                            mStatus = TO_REFRESH;
                        } else {
                            mStatus = DRAG_REFRESH;
                        }
                        layoutSelf();
                        return true;
                    }
                } else {
                    //上拉
                    int dy;
                    if (ViewCompat.canScrollVertically(contentView, 1)) {
                        if (refreshDistance > 0 && (mStatus == DRAG_REFRESH || mStatus == TO_REFRESH)) {
                            //下拉状态中上拉，默认无阻尼
                            dy = (int) (lastY - event.getY());
                            lastY = (int) event.getY();
                            int consumed = refreshAdapter.pullConsumed(dy);
                            refreshDistance -= (dy - consumed);

                            if (refreshDistance < 0) {
                                refreshDistance = 0;
                                mStatus = DRAG_REFRESH;
                            } else if (refreshViewHeight > 0 && refreshDistance > refreshViewHeight) {
                                mStatus = TO_REFRESH;
                            } else {
                                mStatus = DRAG_REFRESH;
                            }
                            layoutSelf();
                            return true;
                        }
                        lastY = (int) event.getY();
                        break;
                    } else {
                        //内容区域上拉到底了
                        dy = (int) ((lastY - event.getY()) / loadResistance);
                        lastY = (int) event.getY();
                        if (mStatus == INIT) {
                            loadAdapter.preShow();
                            mStatus = DRAG_LOAD;
                        }
                        int consumed = loadAdapter.pullConsumed(dy);
                        loadDistance += (dy - consumed);
                        if (loadViewHeight > 0 && loadDistance > loadViewHeight) {
                            mStatus = TO_LOAD;
                        } else {
                            mStatus = DRAG_LOAD;
                        }
                        layoutSelf();
                        return true;
                    }
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (refreshDistance > 0) {
                    generateAnim(refreshDistance, 0, 500, true, INIT);
                    animator.start();
                    break;
                }
                if (loadDistance > 0) {
                    generateAnim(loadDistance, 0, 500, false, INIT);
                    animator.start();
                    break;
                }
//                scroller.startScroll();
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    private Adapter empterRefresh = new Adapter() {
        @Override
        public void preShow() {

        }

        @Override
        public void layout(int contentLeft, int contentTop, int contentRight, int contentBottom) {

        }
    };
    private Adapter empterLoad = new Adapter() {
        @Override
        public void preShow() {

        }

        @Override
        public void layout(int contentLeft, int contentTop, int contentRight, int contentBottom) {

        }
    };
}
