package com.littleyellow.listhelperdemo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.littleyellow.MainBean;
import com.littleyellow.listhelperdemo.adapter.SectionAdapter;
import com.littleyellow.listhelperdemo.adapter.TestAdapter;
import com.littleyellow.listhelperdemo.adapter.TextFlowAdapter;
import com.littleyellow.simple.adapter.LinearAdapter;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.transformer.FadeTransformer;
import com.littleyellow.simple.transformer.ScaleTransformer;
import com.littleyellow.simple.util.Utils;

import java.util.List;

/**
 * Created by 小黄 on 2018/12/12.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private final List<MainBean> data;

    public static LinearAdapter adapter;

    public MainAdapter(List<MainBean> data) {
        this.data = data;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.banner_layout, parent,false);
        final ViewHolder holder = new ViewHolder(v);
        LinearAdapter adapter = null;
        final Context context = parent.getContext();
        int parentWidth = context.getResources().getDisplayMetrics().widthPixels;
        final int viewWidth;
        int offset;
        int dividerHeight;
        switch (viewType){
            case 0:
                adapter = new TestAdapter(null);
                dividerHeight = Utils.dip2px(context,12);
                viewWidth = (int) (parentWidth- dividerHeight*2);
                offset = dividerHeight;
                adapter.setParameters(Parameters.newBuilder()
                        .isLoop(true)
                        .offset(offset)
                        .dividerHeight(dividerHeight)
                        .maxScrollNum(1)
                        .perInch(40f)
                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = (int) (viewWidth/2.5f);
                            }
                        })
                        .build());

                break;
            case 1:
                adapter = new SectionAdapter(null);
                viewWidth = (parentWidth - Utils.dip2px(context,14));
                adapter.setParameters(Parameters.newBuilder()
                        .isLoop(true)
                        .offset(context,7)
                        .dividerHeight(context,14)
                        .maxScrollNum(1)
                        .section(8)
                        .itemPaddingTop(context,5)
                        .itemPaddingBottom(context,10)
                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = params.width/2;
                            }
                        })
                        .scrollListener(new ScrollListener() {
                            int position = -1;

                            @Override
                            public void onScroll(int position, float progress, int total) {

                                if(this.position!=position){
                                    updataIindicator(holder.indicator,position,total);
                                }
                                this.position = position;
                            }
                        })
                        .build());
                break;
            case 2:
                adapter = new TestAdapter(null);
                this.adapter = adapter;
                viewWidth = (int) (parentWidth/1.5- Utils.dip2px(context,12));
                offset = (parentWidth-viewWidth)/2;
                adapter.setParameters(Parameters.newBuilder()
//                        .isLoop(true)
                        .offset(offset)
                        .autoTime(1000)
//                .dividerHeight(dividerHeight)
                        .maxScrollNum(1)
//                        .transformer(new ZoomOutTransformer())
                        .transformer(new ScaleTransformer.Builder().build())
//                .maxScrollNum(3)
                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = (int) (viewWidth/2f);
                            }
                        })
                        .scrollListener(new ScrollListener() {
                            int position = -1;

                            @Override
                            public void onScroll(int position, float progress, int total) {

                                if(this.position!=position){
                                    updataIindicator(holder.indicator,position,total);
                                }
                                this.position = position;
                            }
                        })
                        .build());
                break;
            case 3:
                adapter = new TestAdapter(null);
                dividerHeight = Utils.dip2px(context,12);
                viewWidth = (int) (parentWidth/3- dividerHeight*2);
                offset = dividerHeight;
                adapter.setParameters(Parameters.newBuilder()
                        .offset(offset)
                        .dividerHeight(dividerHeight)
                        .maxScrollNum(2)

                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = viewWidth;
                            }
                        })
                        .build());

                break;
            case 5:
                adapter = new TestAdapter(null);
                dividerHeight = Utils.dip2px(context,12);
                viewWidth = (int) (parentWidth/3- dividerHeight*2);
                offset = dividerHeight;
                adapter.setParameters(Parameters.newBuilder()
                        .offset(offset)
                        .dividerHeight(dividerHeight)
//                        .perInch(500f)
//                        .maxScrollNum(3)
                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = viewWidth;
                            }
                        })
                        .build());

                break;
            case 4:
                holder.itemView.getLayoutParams().height = Utils.dip2px(context,200);
                adapter = new TextFlowAdapter(null);
                dividerHeight = Utils.dip2px(context,12);
                viewWidth = parentWidth;
                adapter.setParameters(Parameters.newBuilder()
                        .isLoop(true)
                        .maxScrollNum(1)
                        .orientation(LinearLayoutManager.VERTICAL)
                        .autoTime(4000)
                        .offset(context,20)
                        .transformer(new FadeTransformer())
                        .autoInterpolator(new DecelerateInterpolator())
                        .acceptScroll(Parameters.LONG_PRESS)
                        .itemHandle(new ItemHandle() {
                            @Override
                            public void setItemParams(RecyclerView.LayoutParams params,int viewType, int parentWith, int totalSize) {
                                params.width = viewWidth;
                                params.height = Utils.dip2px(context,200);
                            }
                        })
                        .build());
                break;
        }
        holder.recyclerview.setAdapter(adapter);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (position){
            case 0:
                break;

            case 1:
                break;
        }

        LinearAdapter adapter = (LinearAdapter) holder.recyclerview.getAdapter();
        adapter.setNewData(data.get(position).getData());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerview;

        private LinearLayout indicator;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview);
            indicator = (LinearLayout) itemView.findViewById(R.id.indicator);
        }
    }


    private void updataIindicator(LinearLayout indicator,int position,int totalSize){
        try {
            indicator.removeAllViews();
            for (int i = 0; i < totalSize; i++) {
                ImageView imageView = new ImageView(indicator.getContext());
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
            Log.e("onSelected",position+"==="+indicator.getChildCount()+"...."
                    +(View.VISIBLE==indicator.getVisibility())
                    +Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
