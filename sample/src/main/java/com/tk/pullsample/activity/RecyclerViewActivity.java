package com.tk.pullsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.pullsample.Config;
import com.tk.pullsample.OnItemClickListener;
import com.tk.pullsample.R;
import com.tk.pullsample.adapter.GridAdapter;
import com.tk.pullsample.adapter.LinearAdapter;
import com.tk.pullsample.adapter.StaggeredAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : RecyclerView
 * </pre>
 */
public class RecyclerViewActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, AnythingPullLayout.OnPullListener, OnItemClickListener {
    private Toolbar toolbar;
    private AnythingPullLayout pullLayout;
    private RecyclerView recyclerview;

    private List<String> list = new ArrayList<>();

    private LinearAdapter linearAdapter;
    private GridAdapter gridAdapter;
    private StaggeredAdapter staggeredAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pullLayout = (AnythingPullLayout) findViewById(R.id.pull_layout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);

        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(this);

        Config config = getIntent().getParcelableExtra("config");
        config.attachToLayout(pullLayout);

        if (!config.refreshEnable || config.refreshMode == 1) {
            for (int i = 0; i < 20; i++) {
                list.add("数据：" + i);
            }
        }
        linearAdapter = new LinearAdapter(list);
        gridAdapter = new GridAdapter(list);
        staggeredAdapter = new StaggeredAdapter(list);

        linearAdapter.setOnItemClickListener(this);
        gridAdapter.setOnItemClickListener(this);
        staggeredAdapter.setOnItemClickListener(this);

        recyclerview.setAdapter(linearAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        pullLayout.setOnPullListener(this);

        pullLayout.autoRefresh();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LinearLayout:
                recyclerview.setLayoutManager(new LinearLayoutManager(this));
                recyclerview.setAdapter(linearAdapter);
                break;
            case R.id.GridLayout:
                GridLayoutManager manager = new GridLayoutManager(this, 3);
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position % 3 + 1;
                    }
                });
                recyclerview.setLayoutManager(manager);
                recyclerview.setAdapter(gridAdapter);
                break;
            case R.id.StaggeredGridLayout:
                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                recyclerview.setAdapter(staggeredAdapter);
                break;
        }
        return false;
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
                recyclerview.getAdapter().notifyDataSetChanged();
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
                recyclerview.getAdapter().notifyItemInserted(list.size());
                recyclerview.smoothScrollToPosition(recyclerview.getAdapter().getItemCount() - 1);
                pullLayout.responseload(true);
            }
        }, 2000);
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, position + " 被点击了", Toast.LENGTH_SHORT).show();
    }
}
