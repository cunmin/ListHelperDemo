package com.littleyellow.simple.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/11/28.
 */

public class VerticalConflictHelper implements RecyclerView.OnItemTouchListener{

    final int mTouchSlop;

    int moveX,moveY;

    private final Parameters parameters;

    public VerticalConflictHelper(Context context,Parameters pt) {
        parameters = pt;
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveX = (int) e.getX();
                moveY = (int) e.getY();
                rv.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) e.getY();
                int x = (int) e.getX();
                if(Math.abs(x-moveX)>mTouchSlop&&Math.abs(x-moveY)<mTouchSlop*2){
                    rv.getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    rv.getParent().requestDisallowInterceptTouchEvent(true);
                    if(!parameters.isLoop) {
                        boolean canScroll = rv.canScrollHorizontally(1) && rv.canScrollHorizontally(-1);
                        if (!canScroll) {
                            rv.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                rv.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
