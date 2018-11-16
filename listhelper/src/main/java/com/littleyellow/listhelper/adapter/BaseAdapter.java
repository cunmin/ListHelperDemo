package com.littleyellow.listhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public abstract class BaseAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    NumProxy numProxy;

    protected Parameters parameters = Parameters.newBuilder().build();

    List<T> data;

    public BaseAdapter(List<T> data){
        this(data,null);
    }

    public BaseAdapter(List<T> data,Parameters parameters) {
        setParameters(parameters);
        this.data = data;
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

    public int getItemType(int position){
        return getItemViewType(numProxy.getPosition(position));
    }

    public abstract K onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(K holder, int position);

    @Override
    public final K onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHoder = onCreateHolder(parent,viewType);
        if(parameters.showCount>0){
            ViewGroup.LayoutParams params = viewHoder.itemView.getLayoutParams();
            int size = getRealityCount();
            int parentWith = 0<parameters.viewWith?parameters.viewWith:viewHoder.itemView.getResources().getDisplayMetrics().widthPixels;
            if(size>parameters.showCount){
                params.width = (int) (parentWith/parameters.showCount);
            }else if(1!=parameters.showCount){
                params.width = parentWith/size;
            }
            params.height = parentWith;
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
        numProxy.iniPosition(recyclerView);//初始化偏移量
    }

    public T getItem(int position){
        return data.get(position);
    }
}
