package com.tk.anythingpull.listview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.ListViewAdapter;
import com.tk.anythingpull.listview.view.PullListView;
import com.tk.anythingpull.view.TestHeadView;
import com.tk.library.implement.IPullDown;
import com.tk.library.implement.IPullUp;
import com.tk.library.view.AnythingPullLayout;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class RFListViewActivity extends AppCompatActivity {
    @Bind(R.id.listview)
    PullListView listview;
    @Bind(R.id.headerview)
    TestHeadView headerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_rf);
        ButterKnife.bind(this);
        listview.setAdapter(new ListViewAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RFListViewActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        pullLayout.setOnPullListener(new AnythingPullLayout.OnPullListener() {
            @Override
            public void refreshing(final IPullDown iPullDown) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullLayout.setRefreshResult(new Random().nextInt(2) > 0);
                    }
                }, 2000);
            }

            @Override
            public void loading(final IPullUp iPullUp) {
            }
        });
    }
}
