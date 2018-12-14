package com.littleyellow.simple.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.animation.Interpolator;

import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.listener.ScrollListener;
import com.littleyellow.simple.transformer.ItemTransformer;
import com.littleyellow.simple.util.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by 小黄 on 2018/11/14.
 */

public class Parameters {

    public boolean isLoop;

    public int parentWidth;

    public int dividerHeight;

    public int autoTime;

    public ScrollListener scrollListener;

    public int offset;

    public ItemHandle itemHandle;

    public int section;

    public int itemPaddingTo;

    public int itemPaddingBottom;

    public ItemTransformer transformer;

    public int maxScrollNum;

    public float perInch;

    public Interpolator autoInterpolator;

    public int orientation;

    public boolean reverseLayout;

    private Parameters(Builder builder) {
        isLoop = builder.isLoop;
        parentWidth = builder.parentWidth;
        dividerHeight = builder.dividerHeight;
        autoTime = builder.autoTime;
        scrollListener = builder.scrollListener;
        offset = builder.offset;
        itemHandle = builder.itemHandle;
        section = builder.section;
        itemPaddingTo = builder.itemPaddingTop;
        itemPaddingBottom = builder.itemPaddingBottom;
        transformer = builder.transformer;
        maxScrollNum = builder.maxScrollNum;//1>builder.maxScrollNum?1:builder.maxScrollNum;
        perInch = builder.perInch;
        autoInterpolator = builder.autoInterpolator;
        orientation = builder.orientation;
        reverseLayout = builder.reverseLayout;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private boolean isLoop;
        private int parentWidth;
        private int dividerHeight;
        private int autoTime = -1;
        private ScrollListener scrollListener;
        private int offset;
        private ItemHandle itemHandle;
        private int section = 1;
        private int itemPaddingTop;
        private int itemPaddingBottom;
        private ItemTransformer transformer;
        private int maxScrollNum;
        private float perInch = 100f;
        private Interpolator autoInterpolator;
        private int orientation;
        private boolean reverseLayout;

        private Builder() {
        }

        public Builder itemHandle(ItemHandle itemHandle) {
            this.itemHandle = itemHandle;
            return this;
        }

        public Builder isLoop(boolean isLoop) {
            this.isLoop = isLoop;
            return this;
        }

        public Builder parentWidth(int viewWith) {
            this.parentWidth = viewWith;
            return this;
        }

        public Builder autoTime(int delayMillis) {
            this.autoTime = delayMillis;
            return this;
        }

        public Builder scrollListener(ScrollListener scrollListener) {
            this.scrollListener = scrollListener;
            return this;
        }

        public Builder offset(int offsetPx) {
            this.offset = offsetPx;
            return this;
        }

        public Builder offset(Context context,int offsetDp) {
            final float scale = context.getResources().getDisplayMetrics().density;
            this.offset = (int) (offsetDp * scale + 0.5f);
            return this;
        }

        public Builder dividerHeight(int dividerHeightPx) {
            this.dividerHeight = dividerHeightPx;
            return this;
        }

        public Builder dividerHeight(Context context,int dividerHeightDp) {
            final float scale = context.getResources().getDisplayMetrics().density;
            this.dividerHeight = (int) (dividerHeightDp * scale + 0.5f);
            return this;
        }

        public Builder section(int section) {
            this.section = section;
            return this;
        }

        public Builder itemPaddingTop(Context context,int topDp) {
            this.itemPaddingTop = Utils.dip2px(context,topDp);
            return this;
        }

        public Builder itemPaddingBottom(Context context,int bottomDp) {
            this.itemPaddingBottom = Utils.dip2px(context,bottomDp);
            return this;
        }

        public Builder itemPaddingTop(int itemPaddingTop) {
            this.itemPaddingTop = itemPaddingTop;
            return this;
        }

        public Builder itemPaddingBottom(int itemPaddingBottom) {
            this.itemPaddingBottom = itemPaddingBottom;
            return this;
        }

        public Builder transformer(ItemTransformer transformer) {
            this.transformer = transformer;
            return this;
        }

        public Builder maxScrollNum(int maxScrollNum) {
            this.maxScrollNum = maxScrollNum;
            return this;
        }

        public Builder perInch(float perInch) {
            this.perInch = perInch;
            return this;
        }

        public Builder autoInterpolator(Interpolator autoInterpolator) {
            this.autoInterpolator = autoInterpolator;
            return this;
        }

        public Builder orientation(@orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder reverseLayout(boolean reverseLayout) {
            this.reverseLayout = reverseLayout;
            return this;
        }



        public Parameters build() {
            return new Parameters(this);
        }
    }

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface orientation{
    }
}
