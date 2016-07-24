package com.tk.anythingpull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tk.anythingpull.R;
import com.tk.anythingpull.scrollview.FFScrollViewActivity;
import com.tk.anythingpull.scrollview.FLScrollViewActivity;
import com.tk.anythingpull.scrollview.FNScrollViewActivity;
import com.tk.anythingpull.scrollview.NFScrollViewActivity;
import com.tk.anythingpull.scrollview.NLScrollViewActivity;
import com.tk.anythingpull.scrollview.RFScrollViewActivity;
import com.tk.anythingpull.scrollview.RLScrollViewActivity;
import com.tk.anythingpull.scrollview.RNScrollViewActivity;

/**
 * Created by TK on 2016/7/23.
 */
public class ScrollViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
    }

    public void REFRESH_NULL(View v) {
        startActivity(new Intent(this, RNScrollViewActivity.class));
    }

    public void NULL_LOAD(View v) {
        startActivity(new Intent(this, NLScrollViewActivity.class));
    }

    public void REFRESH_LOAD(View v) {
        startActivity(new Intent(this, RLScrollViewActivity.class));
    }

    public void FLEX_NULL(View v) {
        startActivity(new Intent(this, FNScrollViewActivity.class));
    }

    public void NULL_FLEX(View v) {
        startActivity(new Intent(this, NFScrollViewActivity.class));
    }

    public void FLEX_FLEX(View v) {
        startActivity(new Intent(this, FFScrollViewActivity.class));
    }

    public void REFRESH_FLEX(View v) {
        startActivity(new Intent(this, RFScrollViewActivity.class));
    }

    public void FLEX_LOAD(View v) {
        startActivity(new Intent(this, FLScrollViewActivity.class));
    }
}
