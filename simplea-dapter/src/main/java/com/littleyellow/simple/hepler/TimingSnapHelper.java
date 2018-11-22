package com.littleyellow.simple.hepler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.adapter.Parameters;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;

/**
 * Created by 小黄 on 2018/11/19.
 */

public class TimingSnapHelper extends BaseSnapHelper{

    private RecyclerView recyclerview;

    private LinearLayoutManager layoutManager;

    private boolean isTouching;

    private Parameters parameters;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!isTouching){
                scrollToNext();
            }
        }
    };

    public TimingSnapHelper(final RecyclerView recyclerview, final Parameters parameters, NumProxy numProxy) {
        super(recyclerview, parameters, numProxy);
        this.recyclerview = recyclerview;
        this.parameters = parameters;
        layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(SCROLL_STATE_TOUCH_SCROLL == newState){
                    isTouching = true;
                }else if(SCROLL_STATE_FLING == newState){
                    isTouching = false;
                }else if(SCROLL_STATE_IDLE == newState){
                    if(!isTouching){
                        recyclerview.removeCallbacks(runnable);
                        recyclerview.postDelayed(runnable,parameters.autoTime);
                    }
                    isTouching = false;
                }
            }
        });
        recyclerview.postDelayed(runnable,parameters.autoTime);
    }
}
