package com.littleyellow.simple.transformer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;
import com.littleyellow.simple.calculate.NumProxy;
import com.littleyellow.simple.helper.LinearSnapHelper;

/**
 * Created by 小黄 on 2018/12/3.
 */

public class TransformerHelper {

    private final ItemTransformer transformer;

    private final RecyclerView recyclerview;

    private final LinearLayoutManager layoutManager;

    private final  Parameters parameters;

    private final LinearSnapHelper helper;

    private NumProxy numProxy;

    private RecyclerView.OnScrollListener scrollListener;

    public TransformerHelper(RecyclerView rv,Parameters ps,NumProxy np) {
        this.recyclerview = rv;
        this.helper = new LinearSnapHelper(ps);
        this.parameters = ps;
        this.transformer = ps.transformer;
        this.numProxy = np;
        this.layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        registerListener();
    }


    private void setHorizontalListener(){
        recyclerview.addOnScrollListener(scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                onScrolledHorizontal(recyclerView);
            }
        });
    }

    private void setVerticalListener(){
        recyclerview.addOnScrollListener(scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                onScrolledVertical(recyclerView);
            }
        });
    }

    private void onScrolledHorizontal(RecyclerView recyclerView){
        int firstPosition = layoutManager.findFirstVisibleItemPosition();//recyclerView.getChildAdapterPosition(currentView);
        int lastPositon = layoutManager.findLastVisibleItemPosition();//recyclerView.getChildAdapterPosition(lastView);
        View snapView =helper.findSnapView(layoutManager);
        int snapPosition = recyclerView.getChildAdapterPosition(snapView);
        if(null!=snapView&&RecyclerView.NO_POSITION!=snapPosition){
            int totalX = snapView.getWidth();
            float reference = ((snapView.getLeft()-parameters.offset)*1.0f)/totalX;
            for(int i=firstPosition;i<=lastPositon;i++){
                View view = layoutManager.findViewByPosition(i);
                if(null != view&&null!=transformer){
                    float progress = (i-snapPosition)+reference;
//                    Log.e("currentView", i + "======" + progress);
                    transformer.transformItem(view,progress);
                }
            }

        }
        if(null!=parameters.scrollListener){
            parameters.scrollListener.onScroll(numProxy.toPosition(snapPosition),0,numProxy.getRealSize());
        }
    }

    private void onScrolledVertical(RecyclerView recyclerView){
        int firstPosition = layoutManager.findFirstVisibleItemPosition();//recyclerView.getChildAdapterPosition(currentView);
        int lastPositon = layoutManager.findLastVisibleItemPosition();//recyclerView.getChildAdapterPosition(lastView);
        View snapView =helper.findSnapView(layoutManager);
        int snapPosition = recyclerView.getChildAdapterPosition(snapView);
        if(null!=snapView&&RecyclerView.NO_POSITION!=snapPosition){
            int totalX = snapView.getHeight();
            float reference = ((snapView.getTop()-parameters.offset)*1.0f)/totalX;
            for(int i=firstPosition;i<=lastPositon;i++){
                View view = layoutManager.findViewByPosition(i);
                if(null != view&&null!=transformer){
                    float progress = (i-snapPosition)+reference;
                    transformer.transformItem(view,progress);
                }
            }
            if(null!=parameters.scrollListener){
                parameters.scrollListener.onScroll(numProxy.toPosition(snapPosition),reference,numProxy.getRealSize());
            }
        }
    }

    public View getSnapView(){
        return helper.findSnapView(layoutManager);
    }

    public int getSnapPosition(){
        View snapView =helper.findSnapView(layoutManager);
        return recyclerview.getChildAdapterPosition(snapView);
    }

    public void registerListener(){
        if(null!=parameters.transformer||null!=parameters.scrollListener){
            recyclerview.post(new Runnable() {
                @Override
                public void run() {
                    recyclerview.removeOnScrollListener(scrollListener);
                    if(layoutManager.canScrollVertically()){
                        setVerticalListener();
                        onScrolledVertical(recyclerview);
                    }else{
                        setHorizontalListener();
                        onScrolledHorizontal(recyclerview);
                    }
                    recyclerview.removeCallbacks(this);
                }
            });
        }
    }

    public void unregisterListener(){
        recyclerview.removeOnScrollListener(scrollListener);
    }
}
