package com.tk.anythingpull.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.tk.anythingpull.R;
import com.tk.library.view.AnythingPullLayout;

/**
 * Created by TK on 2016/7/24.
 */
public class HeaderTestView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;

    public HeaderTestView(Context context) {
        this(context, null);
    }

    public HeaderTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_test, this);
        imageView = (ImageView) view.findViewById(R.id.imageview);
        textView = (TextView) view.findViewById(R.id.textview);
    }

    public void refreshView(int state, float y) {
        ViewHelper.setRotation(imageView, y);
        if (state == AnythingPullLayout.TO_REFRESH) {
            textView.setText("释放立即刷新");
        } else if (state == AnythingPullLayout.REFRESHING) {
            textView.setText("正在刷新...");
            imageView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate_progress));
        } else if (state == AnythingPullLayout.INIT) {
            textView.setText("下拉刷新");
        }
    }
}
