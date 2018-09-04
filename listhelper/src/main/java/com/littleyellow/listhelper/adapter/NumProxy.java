package com.littleyellow.listhelper.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 小黄 on 2018/8/23.
 */

public interface NumProxy {

    void iniPosition(RecyclerView recyclerView);

    int getItemCount();

    int getPosition(int realPosition);
}
