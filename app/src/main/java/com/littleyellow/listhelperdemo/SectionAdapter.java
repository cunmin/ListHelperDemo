package com.littleyellow.listhelperdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.littleyellow.simple.adapter.SimpleAdapter;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public class SectionAdapter extends SimpleAdapter<String,SectionAdapter.ViewHolder> {

    public SectionAdapter(List data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_grid_8, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindHolder(ViewHolder holder, final int position) {
    }

    @Override
    public void onBindSectionHolder(ViewHolder holder, final int startPosition, int endPosition) {
        for(int i = 0;i<holder.grid1.getChildCount();i++){
            LinearLayout itemView = (LinearLayout) holder.grid1.getChildAt(i);
            if(i+startPosition>endPosition){
                itemView.setVisibility(View.INVISIBLE);
                continue;
            }else{
                itemView.setVisibility(View.VISIBLE);
            }
            String text = getItem(i+startPosition);
            TextView textView = (TextView) itemView.getChildAt(1);
            textView.setText(text);
        }

        int reStart = startPosition+parameters.section/2;
        for(int i = 0;i<holder.grid2.getChildCount();i++){
            LinearLayout itemView = (LinearLayout) holder.grid2.getChildAt(i);
            if(i+reStart>endPosition){
                itemView.setVisibility(View.INVISIBLE);
                continue;
            }else{
                itemView.setVisibility(View.VISIBLE);
            }
            String text = getItem(i+reStart);
            TextView textView = (TextView) itemView.getChildAt(1);
            textView.setText(text);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout grid1;

        private LinearLayout grid2;

        public ViewHolder(View itemView) {
            super(itemView);
            grid1 = (LinearLayout) itemView.findViewById(R.id.grid_1);
            grid2 = (LinearLayout) itemView.findViewById(R.id.grid_2);
        }
    }
}
