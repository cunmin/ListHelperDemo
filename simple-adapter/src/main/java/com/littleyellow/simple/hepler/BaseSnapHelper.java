package com.littleyellow.simple.hepler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        Log.e("scrool","scrollToLast");
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  FirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View view = layoutManager.findViewByPosition(FirstVisibleItemPosition);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getLeft()+parameters.dividerHeight+parameters.offset,0);
    }

    protected void scrollToNext(){
        Log.e("scrool","scrollToNext");
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View view = layoutManager.findViewByPosition(firstVisibleItemPosition+1);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getLeft()+parameters.dividerHeight+parameters.offset,0);
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
