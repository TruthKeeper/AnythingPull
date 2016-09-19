package com.tk.anythingpull.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.tk.anythingpull.R;
import com.tk.library.implement.IPullUp;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/24.
 */
public class TestFootView extends LinearLayout implements IPullUp {
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.textview)
    TextView textview;

    public TestFootView(Context context) {
        this(context, null);
    }

    public TestFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_test, this);
        ButterKnife.bind(this, view);
    }

    @Override
    public void startPull() {
        textview.setText("上拉加载");
        imageview.setImageResource(R.drawable.progressbar);
        progressbar.setVisibility(GONE);
    }

    @Override
    public void pull(float distance) {
        ViewHelper.setRotation(imageview, distance);
    }

    @Override
    public void releaseToLoad() {
        textview.setText("释放立即加载");
        imageview.setVisibility(VISIBLE);
        progressbar.setVisibility(GONE);
    }

    @Override
    public void loading() {
        textview.setText("正在加载...");
        imageview.setVisibility(GONE);
        progressbar.setVisibility(VISIBLE);
    }

    @Override
    public void refreshOver(boolean isSuccess) {
        if (isSuccess) {
            textview.setText("加载成功");
            imageview.setImageResource(R.drawable.ok);
        } else {
            textview.setText("加载失败");
            imageview.setImageResource(R.drawable.error);
        }
        imageview.setRotation(0);
        imageview.setVisibility(VISIBLE);
        progressbar.setVisibility(GONE);
    }

    @Override
    public void releaseToInit() {
        textview.setText("上拉加载");
        imageview.setVisibility(VISIBLE);
        progressbar.setVisibility(GONE);
    }
}
