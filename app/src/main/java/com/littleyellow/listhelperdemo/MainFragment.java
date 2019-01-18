package com.littleyellow.listhelperdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.littleyellow.MainBean;
import com.littleyellow.simple.calculate.CommItemDecoration;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.addItemDecoration(CommItemDecoration.createVertical(getContext(), Color.TRANSPARENT, Utils.dip2px(getContext(),20)));
        List<MainBean> data = new ArrayList<>();
        for(int i=5;i>=0;i--){
            MainBean bean = new MainBean(i,10);
            data.add(bean);
        }
//        MainBean bean = new MainBean(4,10);
//            data.add(bean);
        MainAdapter mainAdapter = new MainAdapter(data);
        recyclerView.setAdapter(mainAdapter);
//        init1(view);
//        init2(view);
        Button button = (Button) view.findViewById(R.id.test);
        button.setText("4");
        button.setOnClickListener(new View.OnClickListener() {

            boolean start;
            @Override
            public void onClick(View v) {
//                adapter.smoothScrollToPosition(2);
//                start = !start;

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

//    private void init1(View view){
//        RecyclerView recyclerview = (RecyclerView) view.findViewById(recyclerview1);
//        final LinearLayout indicator = (LinearLayout) view.findViewById(R.id.indicator1);
//        final Context context = getContext();
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
////        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setInitialPrefetchItemCount(3);
////        recyclerview.setItemViewCacheSize(3);
//        recyclerview.setLayoutManager(linearLayoutManager);
//        List<String> data = new ArrayList<>();
//        for(int i=0;i<100;i++){
//            data.add("..."+i+"...");
//        }
//        final TestAdapter adapter = new TestAdapter(data,null);
//        int parentWidth = getResources().getDisplayMetrics().widthPixels;
//        int viewWidth = (int) (parentWidth/2- Utils.dip2px(context,12));
//        int offset = (parentWidth-viewWidth)/2;
//        int dividerHeight = Utils.dip2px(context,12);
//        adapter.setParameters(Parameters.newBuilder()
//                .isLoop(true)
//                .offset(offset)
////                .dividerHeight(dividerHeight)
//                .isPagerMode(true)
////                .autoTime(4000)
//                .transformer(new ZoomOutTransformer())
//                .widthHeight(viewWidth, (int) (viewWidth/2f))
////                .maxScrollNum(3)
//                .itemHandle(new ItemHandle() {
//                    @Override
//                    public void setItemParams(RecyclerView.LayoutParams params, int parentWith, int totalSize) {
//                        params.width = (int) (parentWith/1.5 - Utils.dip2px(context,12));
//                        params.height = (int) (params.width/2.5);
//                    }
//                })
//                .scrollListener(new ScrollListener() {
//                    int position = -1;
//
//                    @Override
//                    public void onScroll(int position, float progress, int total) {
//                        if(this.position!=position){
//                        }
//                        this.position = position;
//                    }
//                })
//                .build());
////        LinearSnapHelper helper = new LinearSnapHelper(adapter.getParameters());
////        LinearSnapHelper helper = new LinearSnapHelper();
////        PagerSnapHelper helper = new PagerSnapHelper();
////        helper.attachToRecyclerView(recyclerview);
//        recyclerview.setAdapter(adapter);
//        Button button = (Button) view.findViewById(R.id.test);
//        button.setText("4");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> data = new ArrayList<>();
//                for(int i=0;i<8;i++){
//                    data.add("..."+i+"...");
//                }
//                if(adapter.getData().isEmpty()){
//                    adapter.setNewData(data);
//                }else{
//                    adapter.setNewData(null);
//                }
//            }
//        });
//    }
//
//    private void init2(View view){
//        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview2);
//        final LinearLayout indicator = (LinearLayout) view.findViewById(R.id.indicator2);
//        final Context context = getContext();
//        List<String> data = new ArrayList<>();
//        for(int i=0;i<22;i++){
//            data.add("..."+i+"...");
//        }
//        SectionAdapter adapter = new SectionAdapter(data);
//        int parentWidth = getResources().getDisplayMetrics().widthPixels;
//        int viewWidth = (parentWidth - Utils.dip2px(context,14));
//        final Button button = (Button) view.findViewById(R.id.test);
//
//        adapter.setParameters(Parameters.newBuilder()
//                .isLoop(true)
//                .offset(context,7)
//                .dividerHeight(context,14)
//                .isPagerMode(true)
//                .autoTime(4000)
//                .section(8)
//                .itemPaddingTop(context,5)
//                .itemPaddingBottom(context,10)
//                .widthHeight(viewWidth,viewWidth/2)
//                .itemHandle(new ItemHandle() {
//                    @Override
//                    public void setItemParams(RecyclerView.LayoutParams params, int parentWith, int totalSize) {
//                        params.width = parentWith - Utils.dip2px(context,14);
//                        params.height = params.width/2;
//                    }
//                })
//                .scrollListener(new ScrollListener() {
//                    int position = -1;
//
//                    @Override
//                    public void onScroll(int position, float progress, int total) {
//
//                        if(this.position!=position){
//                            button.setText((position+1)+"/"+total);
//                            updataIindicator(indicator,position,total);
//                        }
//                        this.position = position;
//                    }
//                })
//                .build());
//        recyclerview.setAdapter(adapter);
//    }
}
