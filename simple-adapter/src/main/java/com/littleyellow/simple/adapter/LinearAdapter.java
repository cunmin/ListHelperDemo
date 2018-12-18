package com.littleyellow.simple.adapter;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.littleyellow.simple.calculate.CommItemDecoration;
import com.littleyellow.simple.calculate.CommonMode;
import com.littleyellow.simple.calculate.FirstOffsetDecoration;
import com.littleyellow.simple.calculate.LoopMode;
import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.calculate.SectionMode;
import com.littleyellow.simple.calculate.TopBottomOffset;
import com.littleyellow.simple.helper.HorizontalConflictHelper;
import com.littleyellow.simple.helper.LinearSnapHelper;
import com.littleyellow.simple.helper.NotScrollHelper;
import com.littleyellow.simple.helper.TimingSnapHelper;
import com.littleyellow.simple.helper.VerticalConflictHelper;
import com.littleyellow.simple.listener.TouchHelper;
import com.littleyellow.simple.transformer.TransformerHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public abstract class LinearAdapter<T,K extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<K>{

    private RecyclerView recyclerView;

    private NumProxy numProxy;

    protected Parameters parameters;

    List<T> data;

    private TimingSnapHelper timingSnapHelper;
    private TransformerHelper transformerHelper;
    private RecyclerView.LayoutManager layoutManager;

    private LinearSnapHelper snapHelper;

    public LinearAdapter(List<T> data){
        this(data,Parameters.newBuilder().build());
    }

    public LinearAdapter(List<T> data, Parameters parameters) {
        this.data = data == null ? new ArrayList<T>() : data;
        setParameters(parameters);
    }

    public void setParameters(Parameters parameters){
        if(null==parameters){
            parameters = Parameters.newBuilder().build();
        }
        this.parameters = parameters;
        numProxy = parameters.isLoop?new LoopMode(data):new CommonMode(data);
        if(1<parameters.section){
            numProxy = new SectionMode(numProxy,parameters.section);
        }
        snapHelper = new LinearSnapHelper(parameters);
    }

    public Parameters getParameters() {
        return parameters;
    }

    public abstract K onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(K holder, int position);

    public void onBindSectionHolder(K holder, int startPosition,int endPosition){}

    @Override
    public final K onCreateViewHolder(ViewGroup parent, int viewType) {
        K viewHoder = onCreateHolder(parent,viewType);
        if(null!=parameters.itemHandle){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) viewHoder.itemView.getLayoutParams();
            if(null==params){
                params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.WRAP_CONTENT);
                viewHoder.itemView.setLayoutParams(params);
            }
            int size = getRealityCount();
            parameters.parentWidth = 0<parameters.parentWidth?parameters.parentWidth:viewHoder.itemView.getResources().getDisplayMetrics().widthPixels;
            parameters.itemHandle.setItemParams(params,viewType,parameters.parentWidth,size);
        }
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        if(parameters.section>1){
            int size = null==data||data.isEmpty()?0:data.size();
            int startIndex = numProxy.toPosition(position)*parameters.section;
            int endIndex = startIndex+parameters.section-1;
            endIndex = endIndex<size?endIndex:size-1;
            onBindSectionHolder(holder,startIndex,endIndex);
        }else{
            onBindHolder(holder,numProxy.toPosition(position));
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = numProxy.getItemCount();
        return itemCount;
    }

    public int getRealityCount(){
        int realityCount = numProxy.getRealSize();
        return realityCount;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        layoutManager = recyclerView.getLayoutManager();
        if(null==layoutManager||!(layoutManager instanceof LinearLayoutManager)) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(),parameters.orientation,parameters.reverseLayout);
            recyclerView.setLayoutManager(linearLayoutManager);
            layoutManager = linearLayoutManager;

        }
        if(0!=parameters.dividerHeight){
            recyclerView.addItemDecoration(CommItemDecoration.createHorizontal(recyclerView.getContext(), Color.TRANSPARENT,parameters.dividerHeight));
        }
        if(0<parameters.autoTime){
            timingSnapHelper = new TimingSnapHelper(recyclerView,snapHelper,parameters);
        }
        if(0<parameters.offset&&!parameters.isLoop){
            recyclerView.addItemDecoration(new FirstOffsetDecoration(parameters));
        }
        if((0!=parameters.itemPaddingTo||0!=parameters.itemPaddingBottom)&&layoutManager.canScrollHorizontally()){
            recyclerView.addItemDecoration(new TopBottomOffset(parameters));
        }
        if(null!=parameters.clickListener||null!=parameters.clickListener){
            recyclerView.addOnItemTouchListener(new TouchHelper(recyclerView));
        }

        if(Parameters.HORIZONTAL==parameters.acceptScroll){
            recyclerView.addOnItemTouchListener(new HorizontalConflictHelper(recyclerView.getContext(),parameters));
        }else if(Parameters.VERTICAL==parameters.acceptScroll){
            recyclerView.addOnItemTouchListener(new VerticalConflictHelper(recyclerView.getContext(),parameters));
        }else if(Parameters.NOTSCROLL==parameters.acceptScroll){
            recyclerView.addOnItemTouchListener(new NotScrollHelper(recyclerView));
        }

//        recyclerView.addOnItemTouchListener(new SlidingConflictHelper(recyclerView.getContext()));
        snapHelper.attachToRecyclerView(recyclerView);

        initPosition();//初始化偏移量
        transformerHelper = new TransformerHelper(recyclerView,parameters,numProxy);
    }

    public T getItem(int position){
        return data.get(position);
    }

    public void setNewData(List<T> data){
        this.data.clear();
        this.data.addAll(data == null ? new ArrayList<T>() : data);
        transformerHelper.unregisterListener();
        notifyDataSetChanged();
        initPosition();
        transformerHelper.registerListener();
        if(null != timingSnapHelper){
            timingSnapHelper.start();
        }
    }

    public List<T> getData() {
        return data;
    }

    public int initPosition(){
        if(null==recyclerView){
            return RecyclerView.NO_POSITION;
        }
        int position = numProxy.getInitPosition();
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(position,parameters.offset);
        return position;
    }

    public void startAuto(){
        if(null!=timingSnapHelper){
            timingSnapHelper.start();
        }
    }

    public void stopAuto(){
        if(null!=timingSnapHelper){
            timingSnapHelper.stop();
        }
    }

    public void smoothScrollToPosition(int position){
        View snapView = snapHelper.findOffsetView(layoutManager);//Utils.getOffsetView(layoutManager,getHorizontalHelper(layoutManager),parameters);
        if(null!=snapView) {
            int position2 = numProxy.toPosition(recyclerView.getChildAdapterPosition(snapView));
            int diff = position - position2;
            if (layoutManager.canScrollVertically()) {
                int snapDistance = snapView.getBottom() + parameters.dividerHeight - parameters.offset;
                int distance = snapDistance + (diff - 1) * (snapView.getHeight() + parameters.dividerHeight);
                recyclerView.smoothScrollBy(0,distance,parameters.autoInterpolator);
            } else {
                int snapDistance = snapView.getRight() + parameters.dividerHeight - parameters.offset;
                int distance = snapDistance + (diff - 1) * (snapView.getWidth() + parameters.dividerHeight);
                recyclerView.smoothScrollBy(distance, 0, parameters.autoInterpolator);
            }
        }
//        if(numProxy.isLoop()){
//            int position2 = getPosition();
//            int diff = position-position2;
//            recyclerView.smoothScrollToPosition(getRealPosition()+diff);
//        }else{
//            if(0<=position&&position<numProxy.getItemCount()){
//                recyclerView.smoothScrollToPosition(position);
//            }
//        }
    }

    public void scrollToPosition(int position){
        View snapView = snapHelper.findOffsetView(layoutManager);//Utils.getOffsetView(layoutManager,getHorizontalHelper(layoutManager),parameters);
        if(null!=snapView) {
            int position2 = numProxy.toPosition(recyclerView.getChildAdapterPosition(snapView));
            int diff = position - position2;
            if (layoutManager.canScrollVertically()) {
                int snapDistance = snapView.getBottom() + parameters.dividerHeight - parameters.offset;
                int distance = snapDistance + (diff - 1) * (snapView.getHeight() + parameters.dividerHeight);
                recyclerView.scrollBy(0,distance);
            } else {
                int snapDistance = snapView.getRight() + parameters.dividerHeight - parameters.offset;
                int distance = snapDistance + (diff - 1) * (snapView.getWidth() + parameters.dividerHeight);
                recyclerView.scrollBy(distance, 0);
            }
        }
//        position = numProxy.toRealPosition(position);
//        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        layoutManager.scrollToPositionWithOffset(position,parameters.offset);
    }

    public View getSnapView(){
        return snapHelper.getSnapView();
    }

    public int getPosition(){
        return numProxy.toPosition(snapHelper.getSnapPosition());
    }

    public int getRealPosition(){
        return snapHelper.getSnapPosition();
    }

    public NumProxy getNumProxy() {
        return numProxy;
    }
}
