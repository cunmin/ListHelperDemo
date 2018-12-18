package com.littleyellow.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.littleyellow.simple.adapter.LinearAdapter;
import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;

/**
 * Created by 小黄 on 2018/11/27.
 */

public class FlowRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;

    private GestureDetector mGestureDetector;

    public FlowRecyclerView(Context context) {
        this(context,null);
    }

    public FlowRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public FlowRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        recyclerView = new RecyclerView(context);
        addView(recyclerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mGestureDetector = new GestureDetector(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener(){ //这里选择SimpleOnGestureListener实现类，可以根据需要选择重写的方法
                    //单击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                        LinearAdapter adapter = getAdapter();
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
                        LinearAdapter adapter = getAdapter();
                        Parameters parameters = adapter.getParameters();
                        if(childView != null && parameters.longClickListener != null){
                            NumProxy numProxy = getAdapter().getNumProxy();
                            parameters.longClickListener.onItemLongClick(childView,numProxy.toPosition(recyclerView.getChildLayoutPosition(childView)),e);
                        }
                    }
                });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        mGestureDetector.onTouchEvent(ev);
        return true;//super.onInterceptTouchEvent(ev);
//        if(mGestureDetector.onTouchEvent(ev)){
//            return true;
//        }else
//            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void setAdapter(LinearAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public LinearAdapter getAdapter(){
        return (LinearAdapter) recyclerView.getAdapter();
    }

    @Deprecated
    @Override
    public void setOnClickListener(OnClickListener l) {

    }

    @Deprecated
    @Override
    public void setOnLongClickListener(OnLongClickListener l) {

    }
}
