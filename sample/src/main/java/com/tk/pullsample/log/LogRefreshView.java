package com.tk.pullsample.log;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tk.pullsample.R;
import com.tk.anythingpull.AnythingPullLayout;
import com.tk.anythingpull.IRefresh;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/14
 *     desc   : 下拉刷新，测试日志
 * </pre>
 */
public class LogRefreshView extends LinearLayout implements IRefresh {
    private static final String TAG = "LogRefreshView";
    private TextView tvDistance;
    private TextView tvStatus;
    private ProgressBar progressbar;

    public LogRefreshView(Context context) {
        this(context, null);
    }

    public LogRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.refresh_log_layout, this);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }


    @Override
    public void preShow() {
        Log.e(TAG, "preShow: ");
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void preDismiss() {
        Log.e(TAG, "preDismiss: ");
        tvStatus.setText("下拉刷新");
    }

    @Override
    public void onDismiss() {
        Log.e(TAG, "onDismiss: ");
        tvStatus.setVisibility(GONE);
    }

    @Override
    public void onPositionChange(boolean touch, int distance, @AnythingPullLayout.Status int status) {
        tvDistance.setText(Integer.toString(distance));
        switch (status) {
            case AnythingPullLayout.INIT:
                Log.e("onPositionChange", "INIT");
                break;
            case AnythingPullLayout.PRE_REFRESH:
                Log.e("onPositionChange", "PRE_REFRESH");
                break;
            case AnythingPullLayout.TO_REFRESH:
                Log.e("onPositionChange", "TO_REFRESH");
                break;
            case AnythingPullLayout.REFRESH_ING:
                Log.e("onPositionChange", "REFRESH_ING");
                break;
            case AnythingPullLayout.REFRESH_RESULT:
                Log.e("onPositionChange", "REFRESH_RESULT");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefreshStart() {
        Log.e(TAG, "onRefreshStart: ");
        tvStatus.setText("刷新中。。。");
    }

    @Override
    public void onRefreshFinish(boolean success) {
        Log.e(TAG, "onRefreshFinish: " + success);
        tvStatus.setText(success ? "刷新成功" : "刷新失败");
    }
}
