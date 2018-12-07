package com.littleyellow.simple.calculate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.littleyellow.simple.adapter.Parameters;

/**
 * Created by 小黄 on 2018/12/3.
 */

public class TransformerHelper {

    private final ItemTransformer transformer;

    private final RecyclerView recyclerview;

    private final  Parameters parameters;

    public TransformerHelper(RecyclerView rv, Parameters ps) {
        this.recyclerview = rv;
        this.parameters = ps;
        this.transformer = ps.transformer;
        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerview.getLayoutManager();
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                View currentView = recyclerview.findChildViewUnder(parameters.offset,parameters.itemPaddingTo);
//                View lastView = recyclerview.findChildViewUnder(recyclerview.getRight(),parameters.itemPaddingTo);
                int firstPosition = layoutManager.findFirstVisibleItemPosition();//recyclerView.getChildAdapterPosition(currentView);
                int lastPositon = layoutManager.findLastVisibleItemPosition();//recyclerView.getChildAdapterPosition(lastView);

                for(int i=firstPosition;i<=lastPositon;i++){
                    View view = layoutManager.findViewByPosition(i);
                    View firstView =layoutManager.findViewByPosition(firstPosition);
                    if(null != view &&null!=view.getLayoutParams()){
                        int totalX;
//                        if(0 == firstPosition){
//                            totalX = (view.getLayoutParams().width+parameters.dividerHeight);
//                        }else{
//                            totalX = (view.getLayoutParams().width+parameters.dividerHeight)*i;
//                        }
                        float reference = 1;
                        if(i==firstPosition){
                            totalX = view.getWidth()+parameters.dividerHeight;

                            reference = ((view.getLeft()-parameters.offset)*1.0f)/totalX;
                            Log.e("currentView", "(" + (view.getLeft()+parameters.offset)*1.0f + ")/" + totalX);
                            Log.e("currentView", i + "======" + reference);
                        }else{
                            totalX = (view.getWidth()+parameters.dividerHeight)*i;
                            reference = ((view.getLeft()-parameters.offset)*1.0f)/totalX;
                        }

                        transformer.transformPage(view,reference);
                    }
                }

//                int firstPosition = layoutManager.findFirstVisibleItemPosition();
//                int lastPosition = layoutManager.findLastVisibleItemPosition();
//                int visibleCount = lastPosition - firstPosition;


//                Log.e("Position",firstPosition+"---"+lastPositon);
//                if(null!=currentView&&null!=currentView.getLayoutParams()) {
//                    int position = layoutManager.getPosition(currentView);
//                    float reference = ((currentView.getLeft()-parameters.offset)*1.0f)/currentView.getWidth();
//                    Log.e("currentView","("+currentView.getLeft()+"-"+parameters.offset+")/"+currentView.getWidth());
//                    Log.e("currentView",position+"======"+reference);
//                    transformer.transformPage(currentView,reference);
//
//                    View nextView = layoutManager.getChildAt(reference<0?position+1:position-1);
//                    Log.e("nextView",(reference<0?position+1:position-1)+"======"+(1-reference));
//                    if(null!=nextView) {
//                        transformer.transformPage(nextView,1-reference);
//                    }
//                }

            }
        });
    }
}
