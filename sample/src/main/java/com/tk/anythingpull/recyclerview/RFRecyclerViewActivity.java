package com.tk.anythingpull.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.RecyclerViewAdapter;
import com.tk.anythingpull.view.HeaderTestView;
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class RFRecyclerViewActivity extends AppCompatActivity {
    @Bind(R.id.recyclerview)
    PullRecyclerview recyclerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    @Bind(R.id.headerview)
    HeaderTestView headerview;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_rf);
        ButterKnife.bind(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new RecyclerViewAdapter(this));
        pullLayout.setOnStatusChangeListener(new AnythingPullLayout.OnStatusChangeListener() {
            @Override
            public void onChange(int status, int direction, float distance) {
                if (direction == 0) {
                    headerview.refreshView(status, distance);
                }
                if (status == AnythingPullLayout.REFRESHING) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pullLayout.setRefreshResult();
                        }
                    }, 2000);
                }
            }
        });
    }
}
