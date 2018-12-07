package com.littleyellow.simple.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

import static com.littleyellow.simple.util.Utils.getFirstView;

/**
 * Created by 小黄 on 2018/11/21.
 */

public abstract class BaseSnapHelper {

    private RecyclerView recyclerview;

    private LinearLayoutManager layoutManager;

    private Parameters parameters;

    public BaseSnapHelper(RecyclerView recyclerview, Parameters parameters){
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
        recyclerview.smoothScrollBy(view.getLeft()-parameters.dividerHeight+parameters.offset,0);
    }

    protected void scrollToNext(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
//        int  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//        View view = layoutManager.findViewByPosition(firstVisibleItemPosition);
        View view = getFirstView(recyclerview,parameters);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getRight()+parameters.dividerHeight-parameters.offset,0);
    }

    protected void scrollToCurrent(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
//        int  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//        View view = layoutManager.findViewByPosition(firstVisibleItemPosition);
        View view = getFirstView(recyclerview,parameters);
        if(null==view){
            return;
        }
        recyclerview.smoothScrollBy(view.getLeft()-parameters.offset,0);
    }

    protected void autoScroll(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
//        int  FirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//        View view = layoutManager.findViewByPosition(FirstVisibleItemPosition);
        View view = getFirstView(recyclerview,parameters);
        if(null==view){
            return;
        }
        int left = parameters.offset-view.getLeft();
        int with = view.getWidth()/2;
        if(left<=with){
            scrollToCurrent();
        }else{
            scrollToNext();
        }
    }

}
