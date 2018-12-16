package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;

import com.littleyellow.listhelperdemo.adapter.TextFlowAdapter;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.transformer.FadeTransformer;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Integer> d = new ArrayList<>();
        for (int i = 0;i<10;i++){
            d.add(i);
        }
        TextFlowAdapter adapter = new TextFlowAdapter(d);
        final int parentWidth = getResources().getDisplayMetrics().widthPixels;
        adapter.setParameters(Parameters.newBuilder()
                .isLoop(true)
                .maxScrollNum(1)
                .orientation(LinearLayoutManager.VERTICAL)
                .autoTime(4000)
                .offset(this,20)
                .transformer(new FadeTransformer())
                .autoInterpolator(new DecelerateInterpolator())
                .acceptScroll(Parameters.LONG_PRESS)
                .itemHandle(new ItemHandle() {
                    @Override
                    public void setItemParams(RecyclerView.LayoutParams params, int viewType, int parentWith, int totalSize) {
                        params.width = parentWidth;
                        params.height = Utils.dip2px(MainActivity.this,200);
                    }
                })
                .build());
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabFragmentHelper.newBuilder()
                .fm(getSupportFragmentManager())
                .mMainViewPager(viewPager)
                .mTabLayout(tabLayout)
                .addTab("简介",MainFragment.class,new Bundle())
                .addTab("评论",MainFragment.class,new Bundle())
//                .addTab("简介",MainFragment.class,new Bundle())
//                .addTab("评论",MainFragment.class,new Bundle())
//                .addTab("简介",MainFragment.class,new Bundle())
//                .addTab("评论",MainFragment.class,new Bundle())
//                .addTab("简介",MainFragment.class,new Bundle())
//                .addTab("评论",MainFragment.class,new Bundle())
                .build()
                .setupViewPager();
    }
}
