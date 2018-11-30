package com.littleyellow.simple.calculate;

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
    public int getInitPosition() {
        if(null==data||data.isEmpty()){
            return -1;
        }
        int position = getItemCount()/data.size()/2*data.size();
        return  position;
    }

    @Override
    public int getItemCount() {
        return null==data||data.isEmpty()?0:Integer.MAX_VALUE;
    }

    @Override
    public int getPosition(int realPosition) {
        if(null==data||data.isEmpty()){
            return -1;
        }
        return (data.size() + realPosition % data.size())%data.size();
    }

    @Override
    public int getRealSize() {
        return null==data?0:data.size();
    }
}
