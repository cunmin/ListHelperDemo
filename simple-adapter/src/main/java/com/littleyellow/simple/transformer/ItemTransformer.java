package com.littleyellow.simple.transformer;

import android.view.View;

/**
 * Created by 小黄 on 2018/12/3.
 */

public interface ItemTransformer {
    /**
     * Apply a property transformation to the given page.
     *
     * @param item Apply the transformation to this item
     * @param position Position of page relative to the current front-and-center
     *                 position of the pager. 0 is front and center. 1 is one full
     *                 page position to the right, and -1 is one page position to the left.
     */
    void transformItem(View item, float position);
}
