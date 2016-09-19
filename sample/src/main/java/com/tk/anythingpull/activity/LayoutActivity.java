package com.tk.anythingpull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tk.anythingpull.R;
import com.tk.anythingpull.layout.FFLayoutViewActivity;
import com.tk.anythingpull.layout.FLLayoutViewActivity;
import com.tk.anythingpull.layout.FNLayoutViewActivity;
import com.tk.anythingpull.layout.NFLayoutViewActivity;
import com.tk.anythingpull.layout.NLLayoutViewActivity;
import com.tk.anythingpull.layout.RFLayoutViewActivity;
import com.tk.anythingpull.layout.RLLayoutViewActivity;
import com.tk.anythingpull.layout.RNLayoutViewActivity;

/**
 * Created by TK on 2016/7/23.
 */
public class LayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
    }

    public void REFRESH_NULL(View v) {
        startActivity(new Intent(this, RNLayoutViewActivity.class));
    }

    public void NULL_LOAD(View v) {
        startActivity(new Intent(this, NLLayoutViewActivity.class));
    }

    public void REFRESH_LOAD(View v) {
        startActivity(new Intent(this, RLLayoutViewActivity.class));
    }

    public void FLEX_NULL(View v) {
        startActivity(new Intent(this, FNLayoutViewActivity.class));
    }

    public void NULL_FLEX(View v) {
        startActivity(new Intent(this, NFLayoutViewActivity.class));
    }

    public void FLEX_FLEX(View v) {
        startActivity(new Intent(this, FFLayoutViewActivity.class));
    }

    public void REFRESH_FLEX(View v) {
        startActivity(new Intent(this, RFLayoutViewActivity.class));
    }

    public void FLEX_LOAD(View v) {
        startActivity(new Intent(this, FLLayoutViewActivity.class));
    }
}
