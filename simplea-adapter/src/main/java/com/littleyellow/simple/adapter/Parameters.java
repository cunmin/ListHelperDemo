package com.littleyellow.simple.adapter;

import android.content.Context;

import com.littleyellow.simple.listener.ScrollListener;

/**
 * Created by 小黄 on 2018/11/14.
 */

public class Parameters {

    public boolean isLoop;

    public double showCount;

    public int viewWith;

    public boolean isPagerMode;

    public float aspectRatio;

    public int dividerHeight;

    public int autoTime;

    public ScrollListener scrollListener;

    public int offset;

    public int itemHeight;

    private Parameters(Builder builder) {
        isLoop = builder.isLoop;
        showCount = builder.showCount;
        viewWith = builder.viewWith;
        isPagerMode = builder.isPagerMode;
        aspectRatio = builder.aspectRatio;
        dividerHeight = builder.dividerHeight;
        autoTime = builder.autoTime;
        scrollListener = builder.scrollListener;
        offset = builder.offset;
        itemHeight = builder.itemHeight;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private boolean isLoop;
        private double showCount;
        private int viewWith;
        private boolean isPagerMode;
        private float aspectRatio;
        private int dividerHeight;
        private int autoTime = -1;
        private ScrollListener scrollListener;
        private int offset;
        private int itemHeight;

        private Builder() {
        }

        public Builder isLoop(boolean isLoop) {
            this.isLoop = isLoop;
            return this;
        }

        public Builder showCount(double showCount) {
            this.showCount = showCount;
            return this;
        }

        public Builder showCount(double showCount,int recyclerViewWith) {
            this.showCount = showCount;
            this.viewWith = recyclerViewWith;
            return this;
        }

        public Builder viewWith(int viewWith) {
            this.viewWith = viewWith;
            return this;
        }

        public Builder isPagerMode(boolean isPagerMode) {
            this.isPagerMode = isPagerMode;
            return this;
        }

        public Builder aspectRatio(float aspectRatio){
            this.aspectRatio = aspectRatio;
            return this;
        }

        public Builder dividerHeight(int px){
            this.dividerHeight = px;
            itemHeight = 0;
            return this;
        }

        public Builder dividerHeight(Context context,int dp){
            final float scale = context.getResources().getDisplayMetrics().density;
            int px = (int) (dp * scale + 0.5f);
            return dividerHeight(px);
        }

        public Builder itemHeight(int px){
            this.itemHeight = px;
            dividerHeight = 0;
            return this;
        }

        public Builder itemHeight(Context context,int dp){
            final float scale = context.getResources().getDisplayMetrics().density;
            int px = (int) (dp * scale + 0.5f);
            return itemHeight(px);
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




        public Parameters build() {
            return new Parameters(this);
        }
    }
}
