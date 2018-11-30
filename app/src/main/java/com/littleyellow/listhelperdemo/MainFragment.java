package com.littleyellow.listhelperdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.littleyellow.simple.SimpleRecyclerView;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends LazyFragment {

    private SimpleRecyclerView recyclerview;

    private TestAdapter adapter;

    LinearLayoutManager layoutManager;

    LinearLayout indicator;

    private void updataIindicator(int position,int totalSize){
        indicator.removeAllViews();
        for (int i = 0; i < totalSize; i++) {
            ImageView imageView = new ImageView(getContext());
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerview = (SimpleRecyclerView) view.findViewById(R.id.recyclerview);
        indicator = (LinearLayout) view.findViewById(R.id.indicator);
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MainActivity activity = (MainActivity) getActivity();
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                }
                return false;
            }
        } ;
        final Context context = getContext();

        List<String> data = new ArrayList<>();
        for(int i=0;i<10;i++){
            data.add("..."+i+"...");
        }
        adapter = new TestAdapter(data,null);
        adapter.setParameters(Parameters.newBuilder()
                .isLoop(true)
                .offset(context,5)
                .dividerHeight(context,10)
                .isPagerMode(true)
                .autoTime(4000)
                .section(3)
                .itemHandle(new ItemHandle() {
                    @Override
                    public void setItemParams(RecyclerView.LayoutParams params, int parentWith, int totalSize) {
                        params.width = parentWith/3 - Utils.dip2px(context,10);
                        params.height = params.width/2;
                    }
                })
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
//        recyclerview.setOnTouchListener(touchListener);
        recyclerview.setAdapter(adapter);
        view.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,TestActivity.class));
//                List<String> data = new ArrayList<>();
//                for(int i=0;i<8;i++){
//                    data.add("..."+i+"...");
//                }
//                if(adapter.getData().isEmpty()){
//                    adapter.setNewData(data);
//                }else{
//                    adapter.setNewData(null);
//                }
                Toast.makeText(getActivity(),recyclerview.getRecyclerView().computeHorizontalScrollExtent()+"---"
                        +recyclerview.getRecyclerView().computeHorizontalScrollOffset()+"---"
                        +recyclerview.getRecyclerView().computeHorizontalScrollRange(),Toast.LENGTH_SHORT).show();

            }
        });
        prepared();
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void inVisible() {

    }
}
