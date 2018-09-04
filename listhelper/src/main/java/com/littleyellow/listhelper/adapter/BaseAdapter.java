package com.littleyellow.listhelper.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public abstract class BaseAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    NumProxy numProxy;

    List<T> data;

    public BaseAdapter(List<T> data){
        this(data,false);
    }

    public BaseAdapter(List<T> data,boolean isLoop) {
        numProxy = isLoop?new LoopMode(data):new CommonMode(data);
        this.data = data;
    }

    public int getItemType(int position){
        return getItemViewType(numProxy.getPosition(position));
    }

    public abstract void onBindHolder(K holder, int position);

    @Override
    public void onBindViewHolder(K holder, int position) {
        onBindHolder(holder,numProxy.getPosition(position));
    }

    @Override
    public int getItemCount() {
        return numProxy.getItemCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        numProxy.iniPosition(recyclerView);//初始化偏移量
    }

    public T getItem(int position){
        return data.get(position);
    }
}
