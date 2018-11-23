package com.littleyellow.simple.hepler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;

/**
 * Created by 小黄 on 2018/11/21.
 */

public abstract class BaseSnapHelper {

    private RecyclerView recyclerview;

    private LinearLayoutManager layoutManager;

    private Parameters parameters;

    public BaseSnapHelper(RecyclerView recyclerview, Parameters parameters, NumProxy numProxy){
        this.recyclerview = recyclerview;
        this.parameters = parameters;
        layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
    }

    protected void scrollToLast(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  FirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View view = layoutManager.findViewByPosition(FirstVisibleItemPosition);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getLeft()-parameters.offset,0);
    }

    protected void scrollToNext(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View view = layoutManager.findViewByPosition(firstVisibleItemPosition);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getWidth()+view.getLeft(),0);
    }

    protected void autoScroll(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  FirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View view = layoutManager.findViewByPosition(FirstVisibleItemPosition);
        if(null==view){
            return;
        }
        int left = -view.getLeft();
        int with = view.getWidth()/2;
        if(left<with){
            scrollToLast();
        }else{
            scrollToNext();
        }
    }

}
