package com.littleyellow.simple.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.listener.ScrollListener;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;
import static com.littleyellow.simple.util.Utils.canCompleScroll;
import static com.littleyellow.simple.util.Utils.getFirstView;

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

    public BannerSnapHelper(final RecyclerView recyclerview, final Parameters parameters, NumProxy numProxy) {
        super(recyclerview, parameters);
        this.recyclerview = recyclerview;
        this.parameters = parameters;
        this.numProxy = numProxy;
        layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(SCROLL_STATE_DRAGGING == newState){
                    isTouching = true;
                }else if(SCROLL_STATE_SETTLING == newState){
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
                    scrollToCurrent();
                }else{
                    boolean canScroll = canCompleScroll(recyclerview,parameters);
                    if(canScroll){
                        scrollToNext();
                    }else{
                        scrollToCurrent();
                    }
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
//        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        View firstView = getFirstView(recyclerview,parameters);
        if(null!=scrollListener&&null!=numProxy&&null!=firstView){
//            firstVisibleItemPosition = parameters.offset>0?firstVisibleItemPosition+1:firstVisibleItemPosition;
            int firstPosition = layoutManager.getPosition(firstView);
            int position = numProxy.getPosition(firstPosition);
            if(selected!=position){
                scrollListener.onSelected(position,numProxy.getRealSize());
            }
            selected = position;
        }
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public int initPosition(){
        int position = numProxy.getInitPosition();
        if(null==layoutManager){
            layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        }
        layoutManager.scrollToPositionWithOffset(position,parameters.offset);
        return position;
    }
}
