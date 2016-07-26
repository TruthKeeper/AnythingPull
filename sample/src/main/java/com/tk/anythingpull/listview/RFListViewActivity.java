package com.tk.anythingpull.listview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.ListViewAdapter;
import com.tk.anythingpull.view.HeaderTestView;
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class RFListViewActivity extends AppCompatActivity {
    @Bind(R.id.listview)
    PullableListView listview;
    @Bind(R.id.headerview)
    HeaderTestView headerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_rf);
        ButterKnife.bind(this);
        listview.setAdapter(new ListViewAdapter());
        pullLayout.setOnStatusChangeListener(new AnythingPullLayout.OnStatusChangeListener() {
            @Override
            public void onChange(int status, int direction, float distance) {
                if (direction == AnythingPullLayout.DIRECTION_DOWN) {
                    headerview.refreshView(status, distance);
                }
                if (status == AnythingPullLayout.LOADING) {
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
