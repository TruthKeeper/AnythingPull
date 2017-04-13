package com.tk.anythingpull.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tk.anythingpull.R;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 重构
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox cbxAutoRefresh;
    private RadioGroup rgpRefresh;
    private RadioButton rbtnRefreshPull;
    private RadioButton rbtnRefreshFlex;
    private RadioButton rbtnRefreshLayer;
    private RadioButton rbtnRefreshDst;
    private CheckBox cbxAutoLoad;
    private RadioGroup rgpLoad;
    private RadioButton rbtnLoadPull;
    private RadioButton rbtnLoadFlex;
    private RadioButton rbtnLoadLayer;
    private RadioButton rbtnLoadDst;
    private TextView btnScrollview;
    private TextView btnListview;
    private TextView btnRecyclerview;
    private TextView btnNested;
    private TextView btnLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        cbxAutoRefresh = (CheckBox) findViewById(R.id.cbx_auto_refresh);
        rgpRefresh = (RadioGroup) findViewById(R.id.rgp_refresh);
        rbtnRefreshPull = (RadioButton) findViewById(R.id.rbtn_refresh_pull);
        rbtnRefreshFlex = (RadioButton) findViewById(R.id.rbtn_refresh_flex);
        rbtnRefreshLayer = (RadioButton) findViewById(R.id.rbtn_refresh_layer);
        rbtnRefreshDst = (RadioButton) findViewById(R.id.rbtn_refresh_dst);
        cbxAutoLoad = (CheckBox) findViewById(R.id.cbx_auto_load);
        rgpLoad = (RadioGroup) findViewById(R.id.rgp_load);
        rbtnLoadPull = (RadioButton) findViewById(R.id.rbtn_load_pull);
        rbtnLoadFlex = (RadioButton) findViewById(R.id.rbtn_load_flex);
        rbtnLoadLayer = (RadioButton) findViewById(R.id.rbtn_load_layer);
        rbtnLoadDst = (RadioButton) findViewById(R.id.rbtn_load_dst);

        btnScrollview = (TextView) findViewById(R.id.btn_scrollview);
        btnListview = (TextView) findViewById(R.id.btn_listview);
        btnRecyclerview = (TextView) findViewById(R.id.btn_recyclerview);
        btnNested = (TextView) findViewById(R.id.btn_nested);
        btnLayout = (TextView) findViewById(R.id.btn_layout);

        btnScrollview.setOnClickListener(this);
        btnListview.setOnClickListener(this);
        btnRecyclerview.setOnClickListener(this);
        btnNested.setOnClickListener(this);
        btnLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scrollview:
                startActivity(new Intent(this, ScrollViewActivity.class));
                break;
            case R.id.btn_listview:
                break;
            case R.id.btn_recyclerview:
                break;
            case R.id.btn_nested:
                break;
            case R.id.btn_layout:
                break;
        }
    }
}
