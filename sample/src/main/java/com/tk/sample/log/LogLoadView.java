package com.tk.sample.log;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
 *     desc   : 上拉加载，测试日志
 * </pre>
 */
public class LogLoadView extends LinearLayout implements ILoad {
    private static final String TAG = "LogLoadView";
    private TextView tvDistance;
    private TextView tvStatus;
    private ProgressBar progressbar;

    public LogLoadView(Context context) {
        this(context, null);
    }

    public LogLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.load_log_layout, this);
        tvDistance = (TextView) findViewById(R.id.tv_distance);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
    }


    @Override
    public void preShow() {
        Log.e(TAG, "preShow: ");
        tvStatus.setVisibility(VISIBLE);
        tvStatus.setText("上拉加载");
    }

    @Override
    public void preDismiss() {
        Log.e(TAG, "preDismiss: ");
        tvStatus.setText("上拉加载");
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
            case AnythingPullLayout.PRE_LOAD:
                Log.e("onPositionChange", "PRE_LOAD");
                break;
            case AnythingPullLayout.TO_LOAD:
                Log.e("onPositionChange", "TO_LOAD");
                break;
            case AnythingPullLayout.LOAD_ING:
                Log.e("onPositionChange", "LOAD_ING");
                break;
            case AnythingPullLayout.LOAD_RESULT:
                Log.e("onPositionChange", "LOAD_RESULT");
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoadStart() {
        Log.e(TAG, "onLoadStart: ");
        tvStatus.setText("加载中。。。");
    }

    @Override
    public void onLoadFinish(boolean success) {
        Log.e(TAG, "onLoadFinish: " + success);
        tvStatus.setText(success ? "加载成功" : "加载失败");
    }
}
