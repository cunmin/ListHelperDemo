package com.littleyellow.simple.helper;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by 小黄 on 2018/11/28.
 */

public class DisallowTouchScroll implements RecyclerView.OnItemTouchListener{


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                rv.setEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
