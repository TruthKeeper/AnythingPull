package com.tk.anythingpull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tk.anythingpull.R;
import com.tk.anythingpull.recyclerview.FFRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.FLRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.FNRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.NFRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.NLRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.RFRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.RLRecyclerViewActivity;
import com.tk.anythingpull.recyclerview.RNRecyclerViewActivity;

/**
 * Created by TK on 2016/7/23.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
    }

    public void REFRESH_NULL(View v) {
        startActivity(new Intent(this, RNRecyclerViewActivity.class));
    }

    public void NULL_LOAD(View v) {
        startActivity(new Intent(this, NLRecyclerViewActivity.class));
    }

    public void REFRESH_LOAD(View v) {
        startActivity(new Intent(this, RLRecyclerViewActivity.class));

    }

    public void FLEX_NULL(View v) {
        startActivity(new Intent(this, FNRecyclerViewActivity.class));

    }

    public void NULL_FLEX(View v) {
        startActivity(new Intent(this, NFRecyclerViewActivity.class));

    }

    public void FLEX_FLEX(View v) {
        startActivity(new Intent(this, FFRecyclerViewActivity.class));

    }

    public void REFRESH_FLEX(View v) {
        startActivity(new Intent(this, RFRecyclerViewActivity.class));

    }

    public void FLEX_LOAD(View v) {
        startActivity(new Intent(this, FLRecyclerViewActivity.class));

    }
}
