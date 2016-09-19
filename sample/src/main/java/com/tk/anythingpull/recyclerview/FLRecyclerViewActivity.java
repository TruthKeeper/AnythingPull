package com.tk.anythingpull.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.RecyclerViewAdapter;
import com.tk.anythingpull.adapter.StaggeredAdapter;
import com.tk.anythingpull.callback.OnRecyclerClickListener;
import com.tk.anythingpull.recyclerview.view.PullRecyclerview;
import com.tk.anythingpull.view.TestFootView;
import com.tk.library.implement.IPullDown;
import com.tk.library.implement.IPullUp;
import com.tk.library.view.AnythingPullLayout;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class FLRecyclerViewActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerview)
    PullRecyclerview recyclerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;
    @Bind(R.id.footview)
    TestFootView footview;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_fl);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new RecyclerViewAdapter(this));
        recyclerview.addOnItemTouchListener(new OnRecyclerClickListener(recyclerview) {
            @Override
            public void onClick(int position) {
                Toast.makeText(FLRecyclerViewActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        pullLayout.setOnPullListener(new AnythingPullLayout.OnPullListener() {
            @Override
            public void refreshing(final IPullDown iPullDown) {
            }

            @Override
            public void loading(final IPullUp iPullUp) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullLayout.setLoadResult(new Random().nextInt(2) > 0);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //拦截菜单键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LinearLayoutManager:
                recyclerview.setLayoutManager(new LinearLayoutManager(this));
                recyclerview.setAdapter(new RecyclerViewAdapter(this));
                break;
            case R.id.GridLayoutManager:
                recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
                recyclerview.setAdapter(new RecyclerViewAdapter(this));
                break;
            case R.id.StaggeredGridLayoutManager:
                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                recyclerview.setAdapter(new StaggeredAdapter(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
