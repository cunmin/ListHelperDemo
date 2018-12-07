package com.littleyellow.simple.helper;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;

import java.lang.ref.WeakReference;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL;
import static com.littleyellow.simple.util.Utils.canCompleScroll;
import static com.littleyellow.simple.util.Utils.getFirstView;

/**
 * Created by 小黄 on 2018/11/19.
 */

public class TimingSnapHelper extends BaseSnapHelper{

    private RecyclerView recyclerview;

    private LinearLayoutManager layoutManager;

    private boolean isTouching;

    private Parameters parameters;

    private DelayHandler handler;

    private final NumProxy numProxy;

    public TimingSnapHelper(final RecyclerView recyclerview, final Parameters parameters, NumProxy numProxy) {
        super(recyclerview, parameters);
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
                    start();
                    isTouching = false;
                }
            }
        });
        handler = new DelayHandler(this);
        start();
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

    public void start(){
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0,parameters.autoTime);
    }

    public void stop(){
        handler.removeMessages(0);
    }

    @Override
    protected void scrollToNext() {
//        if(null==layoutManager){
//            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
//        }
//        int  position = layoutManager.findFirstVisibleItemPosition();
//        View view = layoutManager.findViewByPosition(position);
//        if(null==view){
//            return;
//        }
//        if(view.getRight()+parameters.dividerHeight-parameters.offset==0){
//            view = layoutManager.findViewByPosition(position+1);
//        }
//        if(null==view){
//            return;
//        }
        View firstView = getFirstView(recyclerview,parameters);
        if(null==firstView){
            return;
        }
        boolean canScroll = canCompleScroll(recyclerview,parameters);
        if(canScroll){
            recyclerview.smoothScrollBy(firstView.getRight()+parameters.dividerHeight-parameters.offset,0);
        }
    }
}
