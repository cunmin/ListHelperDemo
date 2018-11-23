package com.littleyellow.simple.hepler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;

import java.lang.ref.WeakReference;

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

    private DelayHandler handler;

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
                        handler.removeMessages(0);
                        handler.sendEmptyMessageDelayed(0,parameters.autoTime);
                    }
                    isTouching = false;
                }
            }
        });
        handler = new DelayHandler(this);
        handler.sendEmptyMessageDelayed(0,parameters.autoTime);
    }

    static class DelayHandler extends Handler{
        final WeakReference<TimingSnapHelper> reference;

        public DelayHandler(TimingSnapHelper timingSnapHelper){
            reference = new WeakReference<>(timingSnapHelper);
        }

        @Override
        public void handleMessage(Message msg) {
            TimingSnapHelper timingSnapHelper = reference.get();
            removeMessages(0);
            if(null!=timingSnapHelper&&!timingSnapHelper.isTouching){
                    timingSnapHelper.scrollToNext();
            }
        }
    }

}
