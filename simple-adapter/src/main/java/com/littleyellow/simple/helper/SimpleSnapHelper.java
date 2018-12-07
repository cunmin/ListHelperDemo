package com.littleyellow.simple.helper;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/12/5.
 */

public class SimpleSnapHelper extends SnapHelper {

    private static final float INVALID_DISTANCE = 1f;

    RecyclerView mRecyclerView;

    private Parameters parameters;

    public SimpleSnapHelper(Parameters parameters) {
        this.parameters = parameters;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {

        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        LinearLayout cardView = (LinearLayout) targetView;
        TextView tv = (TextView) cardView.getChildAt(0);
        Log.e("onScrollStateChanged","calculateDistanceToFinalSnap"+tv.getText().toString()+"->"+out[0]);
        return out;
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper = getHorizontalHelper(layoutManager);
        if (layoutManager instanceof LinearLayoutManager) {
            //找出第一个可见的ItemView的位置
            int firstChildPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            if (firstChildPosition == RecyclerView.NO_POSITION) {
                return null;
            }
            //找到最后一个完全显示的ItemView，如果该ItemView是列表中的最后一个
            //就说明列表已经滑动最后了，这时候就不应该根据第一个ItemView来对齐了
            //要不然由于需要跟第一个ItemView对齐最后一个ItemView可能就一直无法完全显示，
            //所以这时候直接返回null表示不需要对齐
            if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null;
            }

            View firstChildView = layoutManager.findViewByPosition(firstChildPosition);
            //如果第一个ItemView被遮住的长度没有超过一半，就取该ItemView作为snapView
            //超过一半，就把下一个ItemView作为snapView
            View test = mRecyclerView.findChildViewUnder(0,0);
            if (helper.getDecoratedEnd(firstChildView) >= helper.getDecoratedMeasurement(firstChildView) / 2 && helper.getDecoratedEnd(firstChildView) > 0) {
                Log.e("onScrollStateChanged","findSnapView"+firstChildPosition+" test"+mRecyclerView.getChildAdapterPosition(test));
                return firstChildView;
            } else {
                Log.e("onScrollStateChanged","findSnapView"+(firstChildPosition+1)+" test"+mRecyclerView.getChildAdapterPosition(test));
                return layoutManager.findViewByPosition(firstChildPosition + 1);
            }
        } else {
            return null;
        }
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
//        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        final int itemCount = layoutManager.getItemCount();
//        if (itemCount == 0) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        final View currentView = findSnapView(layoutManager);
//        if (currentView == null) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        final int currentPosition = layoutManager.getPosition(currentView);
//        if (currentPosition == RecyclerView.NO_POSITION) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
//                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
//
//        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
//        if (vectorForEnd == null) {
//            return RecyclerView.NO_POSITION;
//        }
//        //计算一屏的item数
//        int deltaThreshold = layoutManager.getWidth() / getHorizontalHelper(layoutManager).getDecoratedMeasurement(currentView);
//
//
//        int deltaJump;
//        if (layoutManager.canScrollHorizontally()) {
//            deltaJump = estimateNextPositionDiffForFling(layoutManager,
//                    getHorizontalHelper(layoutManager), velocityX, 0);
//
//            //对估算出来的位置偏移量进行阈值判断，最多只能滚动一屏的Item个数
//            if (deltaJump > deltaThreshold) {
//                deltaJump = deltaThreshold;
//            }
//            if (deltaJump < -deltaThreshold) {
//                deltaJump = -deltaThreshold;
//            }
//
//            if (vectorForEnd.x < 0) {
//                deltaJump = -deltaJump;
//            }
//        } else {
//            deltaJump = 0;
//        }
//
//        if (deltaJump == 0) {
//            return RecyclerView.NO_POSITION;
//        }
//        int targetPos = currentPosition + deltaJump;
//        if (targetPos < 0) {
//            targetPos = 0;
//        }
//        if (targetPos >= itemCount) {
//            targetPos = itemCount - 1;
//        }
//        Log.e("onScrollStateChanged",targetPos+"------");
//        return targetPos;


        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        View mStartMostChildView = null;
        if (layoutManager.canScrollVertically()) {
            return RecyclerView.NO_POSITION;
        } else if (layoutManager.canScrollHorizontally()) {
            mStartMostChildView = findStartView(layoutManager, getHorizontalHelper(layoutManager));
        }

        if (mStartMostChildView == null) {
            return RecyclerView.NO_POSITION;
        }
        final int centerPosition = layoutManager.getPosition(mStartMostChildView);
        if (centerPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        final boolean forwardDirection;
        if (layoutManager.canScrollHorizontally()) {
            forwardDirection = velocityX > 0;
        } else {
            forwardDirection = velocityY > 0;
        }
        boolean reverseLayout = false;
        if ((layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                    (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
            PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
            if (vectorForEnd != null) {
                reverseLayout = vectorForEnd.x < 0 || vectorForEnd.y < 0;
            }
        }
        return reverseLayout
                ? (forwardDirection ? centerPosition - 1 : centerPosition)
                : (forwardDirection ? centerPosition + 1 : centerPosition);
    }

    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int startest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);

            /** if child is more to start than previous closest, set it as closest  **/
            if (childStart < startest) {
                startest = childStart;
                closestChild = child;
            }
        }
        return closestChild;
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                 OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance =
                Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        if (distance > 0) {
            return (int) Math.floor(distance / distancePerChild);
        } else {
            return (int) Math.ceil(distance / distancePerChild);
        }
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                          OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }

    @Nullable
    private OrientationHelper mHorizontalHelper;
    private RecyclerView.LayoutManager layoutManager;

    @NonNull
    private OrientationHelper getHorizontalHelper( @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null || this.layoutManager != layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
            this.layoutManager = layoutManager;
        }
        return mHorizontalHelper;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    //SnapHelper中该值为100，这里改为40
    private static final float MILLISECONDS_PER_INCH = 100f;
    @Nullable
    protected LinearSmoothScroller createSnapScroller(final RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(), targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }
}
