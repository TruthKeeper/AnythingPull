package com.tk.anythingpull.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.RecyclerViewAdapter;
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class FFRecyclerViewActivity extends AppCompatActivity {
    @Bind(R.id.recyclerview)
    PullRecyclerview recyclerview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_ff);
        ButterKnife.bind(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new RecyclerViewAdapter(this));
    }
}
