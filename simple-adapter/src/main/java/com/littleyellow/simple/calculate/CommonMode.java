package com.littleyellow.simple.calculate;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public class CommonMode implements NumProxy{

    private List data;

    public CommonMode(List data) {
        this.data = data;
    }

    @Override
    public int getInitPosition() {
        return null==data||data.isEmpty()?-1:0;
    }

    @Override
    public int getItemCount() {
        return null==data?0:data.size();
    }

    @Override
    public int toPosition(int realPosition) {
        return realPosition;
    }

    @Override
    public int toRealPosition(int position) {
        return position;
    }

    @Override
    public int getRealSize() {
        return getItemCount();
    }

    @Override
    public boolean isLoop() {
        return false;
    }
}
