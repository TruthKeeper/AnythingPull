package com.tk.pullsample.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.pullsample.R;
import com.tk.pullsample.TestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/17
 *     desc   : 复杂场景
 * </pre>
 */
public class CrazyActivity extends AppCompatActivity implements AnythingPullLayout.OnPullListener {
    private AnythingPullLayout pullLayout;
    private TabLayout tablayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crazy);
        pullLayout = (AnythingPullLayout) findViewById(R.id.pull_layout);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        pullLayout.setOnPullListener(this);

        final List<Fragment> list = new ArrayList<>();
        list.add(new TestFragment());
        list.add(new TestFragment());
        list.add(new TestFragment());
        final String[] titles = new String[]{"第一", "第二", "第三"};
        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        viewpager.setOffscreenPageLimit(3);
        tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onRefreshStart(final AnythingPullLayout pullLayout) {
        Log.e("onRefreshStart", "开始刷新");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullLayout.responseRefresh(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadStart(final AnythingPullLayout pullLayout) {

    }


}
