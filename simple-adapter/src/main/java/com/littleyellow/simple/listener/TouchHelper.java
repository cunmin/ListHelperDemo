package com.littleyellow.simple.listener;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.littleyellow.simple.adapter.LinearAdapter;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;

/**
 * Created by 小黄 on 2018/12/18.
 */

public class TouchHelper implements RecyclerView.OnItemTouchListener{

    private GestureDetector mGestureDetector;

    public TouchHelper(final RecyclerView recyclerView) {
        mGestureDetector = new GestureDetector(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener(){ //这里选择SimpleOnGestureListener实现类，可以根据需要选择重写的方法
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        LinearAdapter adapter = (LinearAdapter) recyclerView.getAdapter();
                        Parameters parameters = adapter.getParameters();
                        if(childView != null && parameters.clickListener != null){
                            NumProxy numProxy = adapter.getNumProxy();
                            parameters.clickListener.onItemClick(childView,numProxy.toPosition(recyclerView.getChildLayoutPosition(childView)),e);
                        }
                        return false;
                    }
                    //长按事件
                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        LinearAdapter adapter = (LinearAdapter) recyclerView.getAdapter();
                        Parameters parameters = adapter.getParameters();
                        if(childView != null && parameters.longClickListener != null){
                            NumProxy numProxy = adapter.getNumProxy();
                            parameters.longClickListener.onItemLongClick(childView,numProxy.toPosition(recyclerView.getChildLayoutPosition(childView)),e);
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
