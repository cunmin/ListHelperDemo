package com.littleyellow.simple.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/11/20.
 */

public class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static boolean canCompleScroll(RecyclerView rv, Parameters pt){
        int offset = rv.computeHorizontalScrollOffset();
        int range = rv.computeHorizontalScrollRange();
        int extent = rv.computeHorizontalScrollExtent();
        if(pt.offset+offset+extent>=range){
            return false;
        }else{
            return true;
        }
    }

    public static View getFirstView(RecyclerView rv, Parameters pt){
        return rv.findChildViewUnder(pt.offset,pt.itemPaddingTo);
    }

}
