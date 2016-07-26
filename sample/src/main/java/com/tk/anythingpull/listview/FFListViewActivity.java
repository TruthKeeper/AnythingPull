package com.tk.anythingpull.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tk.anythingpull.R;
import com.tk.anythingpull.adapter.ListViewAdapter;
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/23.
 */
public class FFListViewActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    PullableListView listview;
    @Bind(R.id.pull_layout)
    AnythingPullLayout pullLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_ff);
        ButterKnife.bind(this);
        listview.setAdapter(new ListViewAdapter());
    }
}
