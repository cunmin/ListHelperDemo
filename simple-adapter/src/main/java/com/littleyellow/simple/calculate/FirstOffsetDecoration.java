package com.littleyellow.simple.calculate;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/11/22.
 */

public class FirstOffsetDecoration extends RecyclerView.ItemDecoration{

    private Parameters parameters;

    public FirstOffsetDecoration(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int postion = parent.getChildAdapterPosition(view);
        if(0 == postion){
            if(parent.getLayoutManager().canScrollVertically()){
                outRect.top = parameters.offset;
            }else{
                outRect.left = parameters.offset;
            }
        }
    }
}
