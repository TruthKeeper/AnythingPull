package com.tk.anythingpull.recyclerview;

import android.os.Bundle;
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
import com.tk.anythingpull.recyclerview.view.YNRecyclerview;
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class FNRecyclerViewActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerview)
    YNRecyclerview recyclerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_fn);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new RecyclerViewAdapter(this));
        recyclerview.addOnItemTouchListener(new OnRecyclerClickListener(recyclerview) {
            @Override
            public void onClick(int position) {
                Toast.makeText(FNRecyclerViewActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
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
