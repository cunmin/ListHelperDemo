package com.littleyellow.simple.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * Created by 小黄 on 2018/12/4.
 */

public abstract class SimpleHolder extends RecyclerView.ViewHolder{

    public SimpleHolder(View v){
        super(v);
        FrameLayout frameLayout = new FrameLayout(v.getContext());
        frameLayout.setLayoutParams(v.getLayoutParams());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        frameLayout.addView(v,params);
        try {
            Field vField =RecyclerView.ViewHolder.class.getDeclaredField("itemView");
            vField.setAccessible(true);
            vField.set(this,frameLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    public View getContainer(View v){
//        FrameLayout frameLayout = new FrameLayout(v.getContext());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
//        frameLayout.addView(v,params);
//        return frameLayout;
//    }
}
