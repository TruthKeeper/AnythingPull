package com.tk.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tk.sample.R;
import com.tk.anythingpull.AnythingPullLayout;
import com.tk.anythingpull.IRefresh;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/14
 *     desc   : 下拉刷新
 * </pre>
 */
public class PullRefreshView extends LinearLayout implements IRefresh {
    private static final String TAG = "PullHeadView";
    private TextView tvDistance;
    private TextView tvStatus;
    private ProgressBar progressbar;

    public PullRefreshView(Context context) {
        this(context, null);
    }

    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.refresh_pull_layout, this);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }


    @Override
    public void preShow() {
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void preDismiss() {
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void onDismiss() {
        progressbar.setVisibility(GONE);
        tvStatus.setVisibility(GONE);
    }

    @Override
    public void onPositionChange(boolean touch, int distance, @AnythingPullLayout.Status int status) {
        tvDistance.setText(Integer.toString(distance));
    }

    @Override
    public void preRefresh() {
        tvStatus.setText("释放立即刷新");
    }

    @Override
    public void onRefreshStart() {
        progressbar.setVisibility(VISIBLE);
        tvStatus.setText("刷新中。。。");
    }

    @Override
    public void onRefreshFinish(boolean success) {
        progressbar.setVisibility(GONE);
        tvStatus.setText(success ? "刷新成功" : "刷新失败");
    }
}
