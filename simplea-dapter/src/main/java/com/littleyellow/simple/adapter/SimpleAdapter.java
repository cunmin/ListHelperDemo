package com.littleyellow.simple.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.littleyellow.simple.calculate.CommItemDecoration;
import com.littleyellow.simple.calculate.CommonMode;
import com.littleyellow.simple.calculate.LeftRightOffset;
import com.littleyellow.simple.calculate.LoopMode;
import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.hepler.BannerSnapHelper;
import com.littleyellow.simple.hepler.TimingSnapHelper;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public abstract class SimpleAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    NumProxy numProxy;

    protected Parameters parameters = Parameters.newBuilder().build();

    List<T> data;

    public SimpleAdapter(List<T> data){
        this(data,null);
    }

    public SimpleAdapter(List<T> data, Parameters parameters) {
        this.data = data;
        setParameters(parameters);
    }

    public void setParameters(Parameters parameters){
        if(null==parameters){
            return;
        }
        this.parameters = parameters;
        numProxy = parameters.isLoop?new LoopMode(data):new CommonMode(data);
    }

    public Parameters getParameters() {
        return parameters;
    }

    public abstract K onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(K holder, int position);

    @Override
    public final K onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHoder = onCreateHolder(parent,viewType);
        if(parameters.showCount>0){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHoder.itemView.getLayoutParams();
            int size = getRealityCount();
            int parentWith = 0<parameters.viewWith?parameters.viewWith:viewHoder.itemView.getResources().getDisplayMetrics().widthPixels;
            if(size>parameters.showCount){
                params.width = (int) (parentWith/parameters.showCount);
            }else if(1!=parameters.showCount){
                params.width = parentWith/size;
            }
            params.width= params.width - parameters.offset*2;
//            params.setMargins(parameters.offset,0,parameters.offset,0);
            if(0 != parameters.aspectRatio){
                params.height = (int) (params.width/parameters.aspectRatio);
            }
        }
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        onBindHolder(holder,numProxy.getPosition(position));
    }

    @Override
    public int getItemCount() {
        return numProxy.getItemCount();
    }

    public int getRealityCount(){
        return null==data?0:data.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        int position = numProxy.iniPosition(recyclerView);//初始化偏移量
        if(parameters.isPagerMode){
            BannerSnapHelper bannerSnapHelper = new BannerSnapHelper(recyclerView,parameters,numProxy);
            bannerSnapHelper.setScrollListener(parameters.scrollListener);
            if(-1!=position&&null!=parameters.scrollListener){
                parameters.scrollListener.onSelected(numProxy.getPosition(position));
            }
        }
        if(0!=parameters.dividerHeight){
            recyclerView.addItemDecoration(CommItemDecoration.createHorizontal(recyclerView.getContext(), Color.TRANSPARENT,parameters.dividerHeight));
        }
        if(0<parameters.autoTime){
            new TimingSnapHelper(recyclerView,parameters,numProxy);
        }
        if(0<parameters.offset){
//            recyclerView.setPadding(parameters.offset,0,parameters.offset,0);
//            recyclerView.setClipChildren(false);
            recyclerView.addItemDecoration(new LeftRightOffset(parameters));
        }
    }

    public T getItem(int position){
        return data.get(position);
    }
}
