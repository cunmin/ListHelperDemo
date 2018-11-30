package com.littleyellow.simple;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.littleyellow.simple.adapter.SimpleAdapter;

/**
 * Created by 小黄 on 2018/11/27.
 */

public class SimpleRecyclerView extends FrameLayout {

    private RecyclerView recyclerView;

    private boolean noScroll = true; //true 代表不能滑动 //false 代表能滑动

    public SimpleRecyclerView(Context context) {
        this(context,null);
    }

    public SimpleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public SimpleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        recyclerView = new RecyclerView(context);
        addView(recyclerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent arg0) {
//        if (noScroll)
//            return false;
//        else
//            return super.onTouchEvent(arg0);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        if (noScroll)
//            return false;
//        else
//            return super.onInterceptTouchEvent(arg0);
//    }

    public void setAdapter(SimpleAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
