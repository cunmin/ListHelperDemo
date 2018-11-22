package com.littleyellow.simple.hepler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.listener.ScrollListener;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

/**
 * Created by 小黄 on 2018/11/19.
 */

public class BannerSnapHelper extends BaseSnapHelper{

    private RecyclerView recyclerview;

    private LinearLayoutManager layoutManager;

    private boolean isTouching;

    private Parameters parameters;

    private ScrollListener scrollListener;

    private NumProxy numProxy;

    private int selected = -1;

    public BannerSnapHelper(RecyclerView recyclerview, Parameters parameters, NumProxy numProxy) {
        super(recyclerview, parameters, numProxy);
        this.recyclerview = recyclerview;
        this.parameters = parameters;
        this.numProxy = numProxy;
        layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(SCROLL_STATE_TOUCH_SCROLL == newState){
                    isTouching = true;
                }else if(SCROLL_STATE_FLING == newState){
                    isTouching = false;
                }else if(SCROLL_STATE_IDLE == newState){
                    if(isTouching){
                        autoScroll();
                    }
                    onSelected();
                    isTouching = false;
                }
            }
        });
        recyclerview.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                if(velocityX<0){
                    scrollToLast();
                }else{
                    scrollToNext();
                }
                onSelected();
                return true;
            }
        });
    }



    private void onSelected(){
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        int  firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        if(null!=scrollListener&&null!=numProxy&&selected!=firstVisibleItemPosition){
            scrollListener.onSelected(numProxy.getPosition(firstVisibleItemPosition));
        }
        selected = firstVisibleItemPosition;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }
}
