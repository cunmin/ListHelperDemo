package com.littleyellow.simple.util;

import android.content.Context;

/**
 * Created by 小黄 on 2018/11/20.
 */

public class Utils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
