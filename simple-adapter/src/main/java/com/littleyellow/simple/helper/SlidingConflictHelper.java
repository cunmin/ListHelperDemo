package com.littleyellow.simple.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by 小黄 on 2018/11/28.
 */

public class SlidingConflictHelper implements RecyclerView.OnItemTouchListener{

    final int mTouchSlop;

    int moveX,moveY;

    public SlidingConflictHelper(Context context) {
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//        if (e.getPointerCount()>1){
//            rv.getParent().requestDisallowInterceptTouchEvent(false);
//            return false;
//        }
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                moveX = (int) e.getX();
                moveY = (int) e.getY();
                rv.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) e.getY();
                int x = (int) e.getX();
                if(Math.abs(y-moveY)>mTouchSlop&&Math.abs(x-moveX)<mTouchSlop*2){
                    rv.getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    rv.getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
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
