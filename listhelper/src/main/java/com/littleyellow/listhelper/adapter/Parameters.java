package com.littleyellow.listhelper.adapter;

/**
 * Created by 小黄 on 2018/11/14.
 */

public class Parameters {

    public boolean isLoop;

    public double showCount;

    public int viewWith;

    private Parameters(Builder builder) {
        isLoop = builder.isLoop;
        showCount = builder.showCount;
        viewWith = builder.viewWith;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private boolean isLoop;
        private double showCount;
        private int viewWith;

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

        public Parameters build() {
            return new Parameters(this);
        }
    }
}
