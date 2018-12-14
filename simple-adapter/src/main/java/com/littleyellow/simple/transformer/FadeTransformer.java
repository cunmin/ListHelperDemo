package com.littleyellow.simple.transformer;

import android.view.View;

/**
 * Created by 小黄 on 2018/12/3.
 */

public class FadeTransformer implements ItemTransformer {

    private static final float MIN_ALPHA = 0f;

    @Override
    public void transformItem(View view, float position) {
        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(MIN_ALPHA);
        } else if (position <= 1) { // [-1,1]
            float scaleFactor = Math.max(MIN_ALPHA, 1 - Math.abs(position));
            view.setAlpha(scaleFactor);
        } else { // (1,+Infinity]
            view.setAlpha(MIN_ALPHA);
        }
        }

}
