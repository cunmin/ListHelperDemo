package com.littleyellow.simple.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.littleyellow.simple.calculate.CommItemDecoration;
import com.littleyellow.simple.calculate.CommonMode;
import com.littleyellow.simple.calculate.LeftOffset;
import com.littleyellow.simple.calculate.LoopMode;
import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.calculate.SectionMode;
import com.littleyellow.simple.helper.BannerSnapHelper;
import com.littleyellow.simple.helper.SlidingConflictHelper;
import com.littleyellow.simple.helper.TimingSnapHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public abstract class SimpleAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    private RecyclerView recyclerView;

    protected NumProxy numProxy;

    protected Parameters parameters = Parameters.newBuilder().build();

    List<T> data;

    private BannerSnapHelper bannerSnapHelper;

    private TimingSnapHelper timingSnapHelper;

    public SimpleAdapter(List<T> data){
        this(data,null);
    }

    public SimpleAdapter(List<T> data, Parameters parameters) {
        this.data = data == null ? new ArrayList<T>() : data;
        setParameters(parameters);
    }

    public void setParameters(Parameters parameters){
        if(null==parameters){
            return;
        }
        this.parameters = parameters;
        numProxy = parameters.isLoop?new LoopMode(data):new CommonMode(data);
        if(1<parameters.section){
            numProxy = new SectionMode(numProxy,parameters.section);
        }
    }

    public Parameters getParameters() {
        return parameters;
    }

    public abstract K onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(K holder, int position);

    @Override
    public final K onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHoder = onCreateHolder(parent,viewType);
//        if(parameters.showCount>0){
//            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHoder.itemView.getLayoutParams();
//            int size = getRealityCount();
//            int parentWith = 0<parameters.viewWith?parameters.viewWith:viewHoder.itemView.getResources().getDisplayMetrics().widthPixels;
//            if(size>parameters.showCount){
//                params.width = (int) (parentWith/(parameters.showCount))-parameters.dividerHeight;
//            }else if(1!=parameters.showCount){
//                params.width = parentWith/size-parameters.dividerHeight;
//            }else {
//                params.width = parentWith;
//            }
//            if(0 != parameters.aspectRatio){
//                params.height = (int) (params.width/parameters.aspectRatio);
//            }
//            if(0 != parameters.itemHeight){
//                params.height = parameters.itemHeight;
//            }
//        }
        if(null!=parameters.itemHandle){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHoder.itemView.getLayoutParams();
            int size = getRealityCount();
            parameters.parentWidth = 0<parameters.parentWidth?parameters.parentWidth:viewHoder.itemView.getResources().getDisplayMetrics().widthPixels;
            parameters.itemHandle.setItemParams(params,parameters.parentWidth,size);
        }
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        onBindHolder(holder,numProxy.getPosition(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = numProxy.getItemCount();
        return itemCount;
    }

    public int getRealityCount(){
        int realityCount = numProxy.getRealSize();
        return realityCount;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(null==layoutManager||!(layoutManager instanceof LinearLayoutManager)) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        if(parameters.isPagerMode){
            bannerSnapHelper = new BannerSnapHelper(recyclerView,parameters,numProxy);
            int position = bannerSnapHelper.initPosition();//初始化偏移量
            if(null!=parameters.scrollListener){
                parameters.scrollListener.onSelected(numProxy.getPosition(position),numProxy.getRealSize());
            }
            bannerSnapHelper.setScrollListener(parameters.scrollListener);
        }
        if(0!=parameters.dividerHeight){
            recyclerView.addItemDecoration(CommItemDecoration.createHorizontal(recyclerView.getContext(), Color.TRANSPARENT,parameters.dividerHeight));
        }
        if(0<parameters.autoTime){
            timingSnapHelper = new TimingSnapHelper(recyclerView,parameters,numProxy);
        }
        if(0<parameters.offset&&!parameters.isLoop){
            recyclerView.addItemDecoration(new LeftOffset(parameters));
        }
        recyclerView.addOnItemTouchListener(new SlidingConflictHelper(recyclerView.getContext()));
    }

    public T getItem(int position){
        return data.get(position);
    }

    public void setNewData(List<T> data){
        this.data.clear();
        this.data.addAll(data == null ? new ArrayList<T>() : data);
        notifyDataSetChanged();
        if(null!=recyclerView&&null!=bannerSnapHelper){
            int position  = bannerSnapHelper.initPosition();
            if(null!=parameters.scrollListener){
                parameters.scrollListener.onSelected(numProxy.getPosition(position),numProxy.getRealSize());
            }
            Log.e("position",position+"====");
        }
        if(null!=timingSnapHelper){
            timingSnapHelper.start();
        }
    }

    public List<T> getData() {
        return data;
    }
}
