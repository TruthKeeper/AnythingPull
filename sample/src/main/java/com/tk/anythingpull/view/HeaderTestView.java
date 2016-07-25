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
import com.tk.library.view.AnythingPullLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by TK on 2016/7/24.
 */
public class HeaderTestView extends LinearLayout {
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.imageview)
    ImageView imageview;
    @Bind(R.id.textview)
    TextView textview;

    public HeaderTestView(Context context) {
        this(context, null);
    }

    public HeaderTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_test, this);
        ButterKnife.bind(this, view);
    }

    public void refreshView(int state, float y) {
        if (state == AnythingPullLayout.TO_REFRESH) {
            ViewHelper.setRotation(imageview, y);
            textview.setText("释放立即刷新");
            imageview.setVisibility(VISIBLE);
            progressbar.setVisibility(GONE);
        } else if (state == AnythingPullLayout.REFRESHING) {
            textview.setText("正在刷新...");
            imageview.setVisibility(GONE);
            progressbar.setVisibility(VISIBLE);
        } else if (state == AnythingPullLayout.INIT) {
            ViewHelper.setRotation(imageview, y);
            textview.setText("下拉刷新");
            imageview.setVisibility(VISIBLE);
            progressbar.setVisibility(GONE);
        }
    }

}
