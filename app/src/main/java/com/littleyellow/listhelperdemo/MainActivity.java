package com.littleyellow.listhelperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private TestAdapter adapter;

    LinearLayoutManager layoutManager;

    LinearLayout indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        indicator = (LinearLayout) findViewById(R.id.indicator);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);

        List<String> data = new ArrayList<>();
        for(int i=0;i<3;i++){
            data.add("..."+i+"...");
        }
        adapter = new TestAdapter(data);
        int with = getResources().getDisplayMetrics().widthPixels- Utils.dip2px(this,20);
        adapter.setParameters(Parameters.newBuilder()
        .isLoop(true)
        .showCount(1)
        .aspectRatio(2)
//         .dividerHeight(with)
        .dividerHeight(this,10)
         .offset(this,5)
//        .itemHeight(this,50)
        .isPagerMode(true)
        .autoTime(4000)
        .scrollListener(new ScrollListener() {
            @Override
            public void onSelected(int position,int totalSize) {
                Log.e("onSelected",position+"----");
                updataIindicator(position,totalSize);
            }

            @Override
            public void onScroll(int dx) {

            }
        })
        .build());
        recyclerview.setAdapter(adapter);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,TestActivity.class));
                List<String> data = new ArrayList<>();
                for(int i=0;i<8;i++){
                    data.add("..."+i+"...");
                }
                if(adapter.getData().isEmpty()){
                    adapter.setNewData(data);
                }else{
                    adapter.setNewData(null);
                }


            }
        });
    }

    private void updataIindicator(int position,int totalSize){
//        int indicators = indicator.getChildCount();
//        if(indicators==totalSize){
//            return;
//        }
        indicator.removeAllViews();
        for (int i = 0; i < totalSize; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 20;
            params.rightMargin = 20;
            if (i == position) {
                imageView.setImageResource(R.mipmap.bg_indicator_yet);
            } else {
                imageView.setImageResource(R.mipmap.bg_indicator_not);
            }
            indicator.addView(imageView, params);
        }
    }
}
