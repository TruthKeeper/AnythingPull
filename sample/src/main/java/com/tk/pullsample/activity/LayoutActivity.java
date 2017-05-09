package com.tk.pullsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.pullsample.Config;
import com.tk.pullsample.R;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 普通布局
 * </pre>
 */
public class LayoutActivity extends AppCompatActivity implements AnythingPullLayout.OnPullListener {
    private AnythingPullLayout pullLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        pullLayout = (AnythingPullLayout) findViewById(R.id.pull_layout);

        Config config = getIntent().getParcelableExtra("config");
        config.attachToLayout(pullLayout);

        pullLayout.setOnPullListener(this);

        pullLayout.autoRefresh();

    }

    @Override
    public void onRefreshStart(final AnythingPullLayout pullLayout) {
        Log.e("onRefreshStart", "开始刷新");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullLayout.responseRefresh(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadStart(final AnythingPullLayout pullLayout) {
        Log.e("onLoadStart", "开始加载");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullLayout.responseload(true);
            }
        }, 2000);
    }
}
