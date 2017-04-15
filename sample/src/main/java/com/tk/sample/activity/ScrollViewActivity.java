package com.tk.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tk.sample.R;
import com.tk.sample.view.PullLoadView;
import com.tk.sample.view.PullRefreshView;
import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : ScrollView
 * </pre>
 */
public class ScrollViewActivity extends AppCompatActivity implements AnythingPullLayout.OnPullListener {
    private AnythingPullLayout pullLayout;
    private PullRefreshView refreshView;
    private PullLoadView loadView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        pullLayout = (AnythingPullLayout) findViewById(R.id.pull_layout);
        refreshView = (PullRefreshView) findViewById(R.id.refresh_view);
        loadView = (PullLoadView) findViewById(R.id.load_view);
        pullLayout.setOnPullListener(this);
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
