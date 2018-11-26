package com.littleyellow.simple.adapter;

import android.content.Context;

import com.littleyellow.simple.calculate.ItemHandle;
import com.littleyellow.simple.listener.ScrollListener;

/**
 * Created by 小黄 on 2018/11/14.
 */

public class Parameters {

    public boolean isLoop;

    public int parentWidth;

    public boolean isPagerMode;

    public int dividerHeight;

    public int autoTime;

    public ScrollListener scrollListener;

    public int offset;

    public ItemHandle itemHandle;

    private Parameters(Builder builder) {
        isLoop = builder.isLoop;
        parentWidth = builder.parentWidth;
        isPagerMode = builder.isPagerMode;
        dividerHeight = builder.dividerHeight;
        autoTime = builder.autoTime;
        scrollListener = builder.scrollListener;
        offset = builder.offset;
        itemHandle = builder.itemHandle;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private boolean isLoop;
        private int parentWidth;
        private boolean isPagerMode;
        private int dividerHeight;
        private int autoTime = -1;
        private ScrollListener scrollListener;
        private int offset;
        private ItemHandle itemHandle;

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

        public Builder isPagerMode(boolean isPagerMode) {
            this.isPagerMode = isPagerMode;
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



        public Parameters build() {
            return new Parameters(this);
        }
    }
}
