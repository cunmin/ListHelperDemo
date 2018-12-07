package com.littleyellow.listhelperdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.calculate.ZoomOutTransformer;
import com.littleyellow.simple.helper.SimpleSnapHelper;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends LazyFragment {

    private void updataIindicator(LinearLayout indicator,int position,int totalSize){
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
        init1(view);
        init2(view);

//        view.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
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
//                Toast.makeText(getActivity(),recyclerview1.computeHorizontalScrollExtent()+"---"
//                        +recyclerview1.computeHorizontalScrollOffset()+"---"
//                        +recyclerview1.computeHorizontalScrollRange(),Toast.LENGTH_SHORT).show();

//            }
//        });

        prepared();
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void inVisible() {

    }

    private void init1(View view){
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview1);
        final LinearLayout indicator = (LinearLayout) view.findViewById(R.id.indicator1);
        final Context context = getContext();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setInitialPrefetchItemCount(3);

        recyclerview.setLayoutManager(linearLayoutManager);
        List<String> data = new ArrayList<>();
        for(int i=0;i<100;i++){
            data.add("..."+i+"...");
        }
        TestAdapter adapter = new TestAdapter(data,null);
        int parentWidth = getResources().getDisplayMetrics().widthPixels;
        int viewWidth = parentWidth- Utils.dip2px(context,12);
        int offset = (parentWidth-viewWidth)/2;
        int dividerHeight = Utils.dip2px(context,12);
        adapter.setParameters(Parameters.newBuilder()
//                .isLoop(true)
//                .offset(offset)
                .dividerHeight(dividerHeight)
//                .isPagerMode(true)
//                .autoTime(4000)
                .transformer(new ZoomOutTransformer())
                .widthHeight(viewWidth, (int) (viewWidth/1.5f))
                .itemHandle(new ItemHandle() {
                    @Override
                    public void setItemParams(RecyclerView.LayoutParams params, int parentWith, int totalSize) {
                        params.width = (int) (parentWith/1.5 - Utils.dip2px(context,12));
                        params.height = (int) (params.width/2.5);
                    }
                })
                .scrollListener(new ScrollListener() {
                    @Override
                    public void onSelected(int position,int totalSize) {
                        Log.e("onSelected",position+"----");
                        updataIindicator(indicator,position,totalSize);
                    }

                    @Override
                    public void onScroll(int dx) {

                    }
                })
                .build());
        SimpleSnapHelper helper = new SimpleSnapHelper(adapter.getParameters());
//        LinearSnapHelper helper = new LinearSnapHelper();
//        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(recyclerview);
        recyclerview.setAdapter(adapter);
        view.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button)v).setText(linearLayoutManager.findLastCompletelyVisibleItemPosition()+"");
            }
        });
    }

    private void init2(View view){
        RecyclerView recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview2);
        final LinearLayout indicator = (LinearLayout) view.findViewById(R.id.indicator2);
        final Context context = getContext();
        List<String> data = new ArrayList<>();
        for(int i=0;i<22;i++){
            data.add("..."+i+"...");
        }
        SectionAdapter adapter = new SectionAdapter(data);
        int parentWidth = getResources().getDisplayMetrics().widthPixels;
        int viewWidth = (parentWidth - Utils.dip2px(context,14));
        adapter.setParameters(Parameters.newBuilder()
                .isLoop(true)
                .offset(context,7)
                .dividerHeight(context,14)
                .isPagerMode(true)
                .autoTime(4000)
                .section(8)
                .itemPaddingTop(context,5)
                .itemPaddingBottom(context,10)
                .widthHeight(viewWidth,viewWidth/2)
                .itemHandle(new ItemHandle() {
                    @Override
                    public void setItemParams(RecyclerView.LayoutParams params, int parentWith, int totalSize) {
                        params.width = parentWith - Utils.dip2px(context,14);
                        params.height = params.width/2;
                    }
                })
                .scrollListener(new ScrollListener() {
                    @Override
                    public void onSelected(int position,int totalSize) {
                        Log.e("onSelected",position+"----");
                        updataIindicator(indicator,position,totalSize);
                    }

                    @Override
                    public void onScroll(int dx) {

                    }
                })
                .build());
        recyclerview.setAdapter(adapter);
    }
}
