package com.tk.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.sample.Config;
import com.tk.sample.R;
import com.tk.sample.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : ListView
 * </pre>
 */
public class ListViewActivity extends AppCompatActivity implements AnythingPullLayout.OnPullListener {
    private AnythingPullLayout pullLayout;
    private ListView listview;

    private List<String> list = new ArrayList<>();
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        pullLayout = (AnythingPullLayout) findViewById(R.id.pull_layout);
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, position + " 被点击了", Toast.LENGTH_SHORT).show();
            }
        });

        Config config = getIntent().getParcelableExtra("config");
        config.attachToLayout(pullLayout);

        if (!config.refreshEnable || config.refreshMode == 1) {
            for (int i = 0; i < 20; i++) {
                list.add("数据：" + i);
            }
        }
        adapter = new ListViewAdapter(list);
        listview.setAdapter(adapter);

        pullLayout.setOnPullListener(this);

        pullLayout.autoRefresh();
    }

    @Override
    public void onRefreshStart(final AnythingPullLayout pullLayout) {
        Log.e("onRefreshStart", "开始刷新");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 0; i < 20; i++) {
                    list.add("数据：" + i);
                }
                adapter.notifyDataSetChanged();
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
                list.add("新数据");
                adapter.notifyDataSetChanged();
                listview.smoothScrollToPosition(adapter.getCount() - 1);
                pullLayout.responseload(true);
            }
        }, 2000);
    }
}
