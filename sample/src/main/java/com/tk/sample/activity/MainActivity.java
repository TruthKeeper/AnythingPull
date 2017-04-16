package com.tk.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tk.sample.Config;
import com.tk.sample.R;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/11
 *     desc   : 重构
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox cbxRefreshFixed;
    private RadioGroup rgpRefresh;
    private RadioButton rbtnRefreshNone;
    private RadioButton rbtnRefreshPull;
    private RadioButton rbtnRefreshFlex;
    private RadioButton rbtnRefreshLayer;
    private RadioButton rbtnRefreshDst;
    private CheckBox cbxLoadFixed;
    private RadioGroup rgpLoad;
    private RadioButton rbtnLoadNone;
    private RadioButton rbtnLoadPull;
    private RadioButton rbtnLoadFlex;
    private RadioButton rbtnLoadLayer;
    private RadioButton rbtnLoadDst;
    private TextView btnScrollview;
    private TextView btnListview;
    private TextView btnRecyclerview;
    private TextView btnNested;
    private TextView btnLayout;
    private TextView btnCrazy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        cbxRefreshFixed = (CheckBox) findViewById(R.id.cbx_refresh_fixed);
        rgpRefresh = (RadioGroup) findViewById(R.id.rgp_refresh);
        rbtnRefreshNone = (RadioButton) findViewById(R.id.rbtn_refresh_none);
        rbtnRefreshPull = (RadioButton) findViewById(R.id.rbtn_refresh_pull);
        rbtnRefreshFlex = (RadioButton) findViewById(R.id.rbtn_refresh_flex);
        rbtnRefreshLayer = (RadioButton) findViewById(R.id.rbtn_refresh_layer);
        rbtnRefreshDst = (RadioButton) findViewById(R.id.rbtn_refresh_dst);
        cbxLoadFixed = (CheckBox) findViewById(R.id.cbx_load_fixed);
        rgpLoad = (RadioGroup) findViewById(R.id.rgp_load);
        rbtnLoadNone = (RadioButton) findViewById(R.id.rbtn_load_none);
        rbtnLoadPull = (RadioButton) findViewById(R.id.rbtn_load_pull);
        rbtnLoadFlex = (RadioButton) findViewById(R.id.rbtn_load_flex);
        rbtnLoadLayer = (RadioButton) findViewById(R.id.rbtn_load_layer);
        rbtnLoadDst = (RadioButton) findViewById(R.id.rbtn_load_dst);
        btnScrollview = (TextView) findViewById(R.id.btn_scrollview);
        btnListview = (TextView) findViewById(R.id.btn_listview);
        btnRecyclerview = (TextView) findViewById(R.id.btn_recyclerview);
        btnNested = (TextView) findViewById(R.id.btn_nested);
        btnLayout = (TextView) findViewById(R.id.btn_layout);
        btnCrazy = (TextView) findViewById(R.id.btn_crazy);

        btnScrollview.setOnClickListener(this);
        btnListview.setOnClickListener(this);
        btnRecyclerview.setOnClickListener(this);
        btnNested.setOnClickListener(this);
        btnLayout.setOnClickListener(this);
        btnCrazy.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_scrollview:
                intent = new Intent(this, ScrollViewActivity.class);
                intent.putExtra("config", initConfig());
                startActivity(intent);
                break;
            case R.id.btn_listview:
                intent = new Intent(this, ListViewActivity.class);
                intent.putExtra("config", initConfig());
                startActivity(intent);
                break;
            case R.id.btn_recyclerview:
                break;
            case R.id.btn_nested:
                break;
            case R.id.btn_layout:
                break;
            case R.id.btn_crazy:
                break;
        }
    }

    private Config initConfig() {
        Config config = new Config();
        config.refreshFixed = cbxRefreshFixed.isChecked();
        switch (rgpRefresh.getCheckedRadioButtonId()) {
            case R.id.rbtn_refresh_none:
                config.refreshEnable = false;
                break;
            case R.id.rbtn_refresh_pull:
                config.refreshMode = 0;
                break;
            case R.id.rbtn_refresh_flex:
                config.refreshMode = 1;
                break;
            case R.id.rbtn_refresh_layer:
                config.refreshMode = 2;
                break;
            case R.id.rbtn_refresh_dst:
                config.refreshMode = 3;
                break;
        }

        config.loadFixed = cbxLoadFixed.isChecked();
        switch (rgpLoad.getCheckedRadioButtonId()) {
            case R.id.rbtn_load_none:
                config.loadEnable = false;
                break;
            case R.id.rbtn_load_pull:
                config.loadMode = 0;
                break;
            case R.id.rbtn_load_flex:
                config.loadMode = 1;
                break;
            case R.id.rbtn_load_layer:
                config.loadMode = 2;
                break;
            case R.id.rbtn_load_dst:
                config.loadMode = 3;
                break;
        }
        return config;
    }
}
