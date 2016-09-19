package com.tk.anythingpull.scrollview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tk.anythingpull.R;
import com.tk.anythingpull.view.TestFootView;
import com.tk.library.view.AnythingPullLayout;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class FLScrollViewActivity extends AppCompatActivity {
    @Bind(R.id.footview)
    TestFootView footview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview_fl);
        ButterKnife.bind(this);
        pullLayout.setOnPullListener(new AnythingPullLayout.OnPullListener() {
            @Override
            public void refreshing() {
            }

            @Override
            public void loading() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullLayout.setLoadResult(new Random().nextInt(2) > 0);
                    }
                }, 2000);
            }
        });
    }
}
