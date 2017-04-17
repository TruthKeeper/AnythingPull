package com.tk.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tk.anythingpull.AnythingPullLayout;
import com.tk.sample.adapter.LinearAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/17
 *     desc   : xxxx描述
 * </pre>
 */
public class TestFragment extends Fragment implements AnythingPullLayout.OnPullListener {
    private AnythingPullLayout pullLayout;
    private RecyclerView recyclerview;

    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        pullLayout = (AnythingPullLayout) view.findViewById(R.id.pull_layout);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerview.setAdapter(new LinearAdapter(list));

        pullLayout.setOnPullListener(this);

        pullLayout.autoRefresh();
    }

    @Override
    public void onRefreshStart(final AnythingPullLayout pullLayout) {
        Log.e("onRefreshStart", "开始刷新");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 0; i < 20; i++) {
                    list.add("数据：" + i);
                }
                recyclerview.getAdapter().notifyDataSetChanged();
                pullLayout.responseRefresh(true);
            }
        }, 2000);
    }

    @Override
    public void onLoadStart(final AnythingPullLayout pullLayout) {
        Log.e("onLoadStart", "开始加载");
        pullLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.add("新数据");
                recyclerview.getAdapter().notifyItemInserted(list.size());
                recyclerview.smoothScrollToPosition(recyclerview.getAdapter().getItemCount() - 1);
                pullLayout.responseload(true);
            }
        }, 2000);
    }
}
