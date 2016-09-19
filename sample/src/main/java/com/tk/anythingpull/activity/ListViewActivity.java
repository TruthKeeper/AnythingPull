package com.tk.anythingpull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tk.anythingpull.R;
import com.tk.anythingpull.listview.FFListViewActivity;
import com.tk.anythingpull.listview.FLListViewActivity;
import com.tk.anythingpull.listview.FNListViewActivity;
import com.tk.anythingpull.listview.NFListViewActivity;
import com.tk.anythingpull.listview.NLListViewActivity;
import com.tk.anythingpull.listview.RFListViewActivity;
import com.tk.anythingpull.listview.RLListViewActivity;
import com.tk.anythingpull.listview.RNListViewActivity;

/**
 * Created by TK on 2016/7/23.
 */
public class ListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
    }

    public void REFRESH_NULL(View v) {
        startActivity(new Intent(this, RNListViewActivity.class));
    }

    public void NULL_LOAD(View v) {
        startActivity(new Intent(this, NLListViewActivity.class));
    }

    public void REFRESH_LOAD(View v) {
        startActivity(new Intent(this, RLListViewActivity.class));
    }

    public void FLEX_NULL(View v) {
        startActivity(new Intent(this, FNListViewActivity.class));
    }

    public void NULL_FLEX(View v) {
        startActivity(new Intent(this, NFListViewActivity.class));
    }

    public void FLEX_FLEX(View v) {
        startActivity(new Intent(this, FFListViewActivity.class));
    }

    public void REFRESH_FLEX(View v) {
        startActivity(new Intent(this, RFListViewActivity.class));
    }

    public void FLEX_LOAD(View v) {
        startActivity(new Intent(this, FLListViewActivity.class));
    }
}
