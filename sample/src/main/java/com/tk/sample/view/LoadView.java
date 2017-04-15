package com.tk.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.anythingpull.ILoad;
import com.tk.sample.R;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/14
 *     desc   : 上拉加载
 * </pre>
 */
public class LoadView extends LinearLayout implements ILoad {
    private static final String TAG = "LoadView";
    private TextView tvStatus;
    private ImageView imageView;
    private RotateAnimation rotateAnimation;

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.common_layout, this);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        imageView = (ImageView) findViewById(R.id.image);
    }

    private void startAnim() {
        rotateAnimation = new RotateAnimation(0, 360,
                imageView.getMeasuredWidth() >> 1, imageView.getMeasuredHeight() >> 1);
        rotateAnimation.setDuration(750);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotateAnimation);
    }

    @Override
    public void preShow() {
        imageView.setVisibility(VISIBLE);
        tvStatus.setText("上拉加载");
    }

    @Override
    public void preDismiss() {
        tvStatus.setText("上拉加载");
    }

    @Override
    public void onDismiss() {
        imageView.clearAnimation();
        imageView.setVisibility(GONE);
    }

    @Override
    public void onPositionChange(boolean touch, int distance, @AnythingPullLayout.Status int status) {
        if (status != AnythingPullLayout.LOAD_ING && status != AnythingPullLayout.LOAD_RESULT) {
            imageView.setRotation(distance);
        }
        if (status == AnythingPullLayout.TO_LOAD) {
            tvStatus.setText("释放立即加载");
        } else if (status == AnythingPullLayout.PRE_LOAD) {
            tvStatus.setText("上拉加载");
        }
    }

    @Override
    public void onLoadStart() {
        startAnim();
        tvStatus.setText("加载中。。。");
    }

    @Override
    public void onLoadFinish(boolean success) {
        imageView.clearAnimation();
        imageView.setVisibility(GONE);
        tvStatus.setText(success ? "加载成功" : "加载失败");
    }
}
