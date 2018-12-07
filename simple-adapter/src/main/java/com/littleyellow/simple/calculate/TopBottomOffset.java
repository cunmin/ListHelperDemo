package com.littleyellow.simple.calculate;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/11/22.
 */

public class TopBottomOffset extends RecyclerView.ItemDecoration{

    private Parameters parameters;

    public TopBottomOffset(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = parameters.itemPaddingTo;
        outRect.bottom = parameters.itemPaddingBottom;
    }
}
