package com.littleyellow.simple.calculate;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public class LoopMode implements NumProxy{

    private List data;

    public LoopMode(List data) {
        this.data = data;
    }

    @Override
    public int iniPosition(RecyclerView recyclerView) {
        if(null==data||data.isEmpty()){
            return -1;
        }
        int position = getItemCount()/data.size()/2*data.size();
        recyclerView.scrollToPosition(position);
        return  position;
    }

    @Override
    public int getItemCount() {
        return null==data?0:Integer.MAX_VALUE;
    }

    @Override
    public int getPosition(int realPosition) {
        return (data.size() + realPosition % data.size())%data.size();
    }
}
