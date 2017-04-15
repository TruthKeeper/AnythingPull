package com.tk.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tk.sample.R;
import com.tk.anythingpull.AnythingPullLayout;
import com.tk.anythingpull.ILoad;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/14
 *     desc   : 上拉加载
 * </pre>
 */
public class PullLoadView extends LinearLayout implements ILoad {
    private TextView tvDistance;
    private TextView tvStatus;
    private ProgressBar progressbar;

    public PullLoadView(Context context) {
        this(context, null);
    }

    public PullLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.load_pull_layout, this);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void preShow() {
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setText("上拉加载");
    }

    @Override
    public void preDismiss() {
        tvStatus.setText("上拉加载");
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
    public void preLoad() {
        tvStatus.setText("释放立即加载");
    }

    @Override
    public void onLoadStart() {
        progressbar.setVisibility(VISIBLE);
        tvStatus.setText("加载中。。。");
    }

    @Override
    public void onLoadFinish(boolean success) {
        progressbar.setVisibility(GONE);
        tvStatus.setText(success ? "加载成功" : "加载失败");
    }
}
