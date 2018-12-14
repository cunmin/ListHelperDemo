package com.littleyellow.simple.util;

import android.content.Context;
import android.support.v7.widget.OrientationHelper;
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

    public static View getFirstView(RecyclerView rv, Parameters pt){
        return rv.findChildViewUnder(pt.offset,pt.itemPaddingTo);
    }

    public static View getOffsetView(RecyclerView.LayoutManager layoutManager,
                                     OrientationHelper helper,Parameters pt){
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int absClosest = Integer.MAX_VALUE;
        int offset;
        if (layoutManager.getClipToPadding()) {
            offset = helper.getStartAfterPadding() + pt.offset;
        } else {
            offset = pt.offset;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);
            int absDistance = Math.abs(childStart - offset);

            /** if child center is closer than previous closest, set it as closest  **/
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

}
