package com.littleyellow.simple.calculate;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 小黄 on 2018/11/26.
 */

public interface ItemHandle {

    void setItemParams(RecyclerView.LayoutParams params,int viewType,int parentWith,int totalSize);

}
