package com.littleyellow.simple.helper;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/12/5.
 */

public class LinearSnapHelper extends SnapHelper {

    private static final float INVALID_DISTANCE = 1f;

    RecyclerView mRecyclerView;

    private Parameters parameters;

    public LinearSnapHelper(Parameters parameters) {
        this.parameters = parameters;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToSnap(layoutManager, targetView,
                    getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        if (layoutManager.canScrollVertically()) {
            out[1] = distanceToSnap(layoutManager, targetView,
                    getVerticalHelper(layoutManager));
        } else {
            out[1] = 0;
        }
        return out;
    }

    @Nullable
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if(parameters.maxScrollNum<1){
            return null;
        }
        return findOffsetView(layoutManager);
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if(parameters.maxScrollNum<1){
            return RecyclerView.NO_POSITION;
        }

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;

        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            return RecyclerView.NO_POSITION;
        }



        int hDeltaJump;
        if (layoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump;
            }
            if((0==hDeltaJump||1==parameters.maxScrollNum)&&0!=velocityX){
                if(currentView.getLeft()>parameters.offset){
                    hDeltaJump += 0<velocityX?0:-1;
                }else{
                    hDeltaJump += velocityX>0?1:-1;
                }
            }
        } else {
            hDeltaJump = 0;
        }

        int vDeltaJump;
        if (layoutManager.canScrollVertically()) {
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0) {
                vDeltaJump = -vDeltaJump;
            }
            if((0==vDeltaJump||1==parameters.maxScrollNum)&&0!=velocityY){
                if(currentView.getTop()>parameters.offset){
                    vDeltaJump += 0<velocityY?0:-1;
                }else{
                    vDeltaJump += velocityY>0?1:-1;
                }
            }
        } else {
            vDeltaJump = 0;
        }

        OrientationHelper orientationHelper;
        int deltaJump;
        int measure;
        if(layoutManager.canScrollVertically()){
            deltaJump = vDeltaJump;
            orientationHelper = getVerticalHelper(layoutManager);
            measure = currentView.getHeight();
        }else {
            deltaJump = hDeltaJump;
            orientationHelper = getHorizontalHelper(layoutManager);
            measure = currentView.getWidth();
        }

        int deltaThreshold = parameters.maxScrollNum;// (measure+parameters.offset);
        if (deltaJump > deltaThreshold) {
            deltaJump = deltaThreshold;
        }
        if (deltaJump < -deltaThreshold) {
            deltaJump = -deltaThreshold;
        }
//        if (deltaJump == 0) {
//            return RecyclerView.NO_POSITION;
//        }
        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }

        return targetPos;


//        final int itemCount = layoutManager.getItemCount();
//        if (itemCount == 0) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        View mStartMostChildView = null;
//        if (layoutManager.canScrollVertically()) {
//            mStartMostChildView = findOffsetView(layoutManager, getVerticalHelper(layoutManager));
//        } else if (layoutManager.canScrollHorizontally()) {
//            mStartMostChildView = findOffsetView(layoutManager, getHorizontalHelper(layoutManager));
//        }
//
//        if (mStartMostChildView == null) {
//            return RecyclerView.NO_POSITION;
//        }
//        final int centerPosition = layoutManager.getPosition(mStartMostChildView);
//        if (centerPosition == RecyclerView.NO_POSITION) {
//            return RecyclerView.NO_POSITION;
//        }
//
//        final boolean forwardDirection;
//        if (layoutManager.canScrollHorizontally()) {
//            forwardDirection = velocityX > 0;
//        } else {
//            forwardDirection = velocityY > 0;
//        }
//        boolean reverseLayout = false;
//        if ((layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
//            RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
//                    (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
//            PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
//            if (vectorForEnd != null) {
//                reverseLayout = vectorForEnd.x < 0 || vectorForEnd.y < 0;
//            }
//        }
//        return reverseLayout
//                ? (forwardDirection ? centerPosition - 1 : centerPosition)
//                : (forwardDirection ? centerPosition + 1 : centerPosition);
    }

    public View findOffsetView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper;
        if (layoutManager.canScrollVertically()) {
            helper = getVerticalHelper(layoutManager);
        } else {
            helper = getHorizontalHelper(layoutManager);
        }
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        int absClosest = Integer.MAX_VALUE;
        int offset = getOffset(layoutManager,helper);
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

    private int getOffset(RecyclerView.LayoutManager layoutManager,OrientationHelper helper){
        final int offset;
        if (layoutManager.getClipToPadding()) {
            offset = helper.getStartAfterPadding() + parameters.offset;
        } else {
            offset = parameters.offset;
        }
        return offset;
    }


    public View getSnapView(){
        return findOffsetView(layoutManager);
    }

    public int getSnapPosition(){
        View snapView =getSnapView();
        return layoutManager.getPosition(snapView);
    }
    /**
     * ===================================================================
     */
    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;
    private RecyclerView.LayoutManager layoutManager;

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

    @NonNull
    public OrientationHelper getHorizontalHelper( @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null || this.layoutManager != layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
            this.layoutManager = layoutManager;
        }
        return mHorizontalHelper;
    }

    public OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null || this.layoutManager != layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
            this.layoutManager = layoutManager;
        }
        return mVerticalHelper;
    }

    private int distanceToSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                 @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView);
        final int offset = getOffset(layoutManager,helper);
        return childCenter - offset;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

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
                return parameters.perInch / displayMetrics.densityDpi;
            }
        };
    }
}
