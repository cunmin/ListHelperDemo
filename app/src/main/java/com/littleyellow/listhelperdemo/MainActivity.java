package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.littleyellow.listhelperdemo.adapter.TextFlowAdapter;
import com.littleyellow.simple.FlowRecyclerView;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.listener.ItemClickListener;
import com.littleyellow.simple.listener.ItemLongClickListener;
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
//                .isLoop(true)
                .maxScrollNum(1)
                .orientation(LinearLayoutManager.VERTICAL)
                .autoTime(4000)
                .offset(this,20)
                .transformer(new FadeTransformer())
                .acceptScroll(Parameters.NOTSCROLL)
                .autoInterpolator(new DecelerateInterpolator())
                .clickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position,MotionEvent e) {
                        Toast.makeText(view.getContext(),position+"onItemClick",Toast.LENGTH_SHORT).show();
                    }
                })
                .longClickListener(new ItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position,MotionEvent e) {
                        Toast.makeText(view.getContext(),position+"onItemLongClick",Toast.LENGTH_SHORT).show();
                    }
                })
                .itemHandle(new ItemHandle() {
                    @Override
                    public void setItemParams(RecyclerView.LayoutParams params, int viewType, int parentWith, int totalSize) {
                        params.width = parentWidth;
                        params.height = Utils.dip2px(MainActivity.this,200);
                    }
                })
                .build());
        FlowRecyclerView recyclerview = (FlowRecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setAdapter(adapter);
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
