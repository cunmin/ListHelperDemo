package com.littleyellow.simple.calculate;

import android.support.v7.widget.RecyclerView;

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
    public int iniPosition(RecyclerView recyclerView) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return null==data?0:data.size();
    }

    @Override
    public int getPosition(int realPosition) {
        return realPosition;
    }

    @Override
    public int getRealSize() {
        return getItemCount();
    }
}
