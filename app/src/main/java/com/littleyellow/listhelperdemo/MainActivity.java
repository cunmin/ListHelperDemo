package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabFragmentHelper.newBuilder()
                .fm(getSupportFragmentManager())
                .mMainViewPager(viewPager)
                .mTabLayout(tabLayout)
                .addTab("简介",MainFragment.class,new Bundle())
//                .addTab("评论",MainFragment.class,new Bundle())
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
