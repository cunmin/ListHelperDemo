package com.littleyellow.simple.calculate;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 小黄 on 2018/8/23.
 */

public interface NumProxy {

    int iniPosition(RecyclerView recyclerView);

    int getItemCount();

    int getPosition(int realPosition);

    int getRealSize();
}
