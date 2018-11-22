package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private TestAdapter adapter;

    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);

        List<String> data = new ArrayList<>();
        for(int i=0;i<20;i++){
            data.add("..."+i+"...");
        }
        adapter = new TestAdapter(data);
        int with = getResources().getDisplayMetrics().widthPixels- Utils.dip2px(this,20);
        adapter.setParameters(Parameters.newBuilder()
        .isLoop(true)
        .showCount(1)
        .aspectRatio(2)
//         .dividerHeight(with)
//        .dividerHeight(this,10)
         .offset(this,10)
        .isPagerMode(true)
        .autoTime(4000)
        .scrollListener(new ScrollListener() {
            @Override
            public void onSelected(int position) {
                Log.e("onSelected",position+"----");
            }

            @Override
            public void onScroll(int dx) {

            }
        })
        .build());
        recyclerview.setAdapter(adapter);
    }
}
