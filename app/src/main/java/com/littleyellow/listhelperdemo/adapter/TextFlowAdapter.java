package com.littleyellow.listhelperdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littleyellow.simple.adapter.LinearAdapter;
import com.littleyellow.simple.util.Utils;

import java.util.List;

/**
 * Created by 小黄 on 2018/12/13.
 */

public class TextFlowAdapter extends LinearAdapter<Integer,TextFlowAdapter.ViewHolder> {


    public TextFlowAdapter(List<Integer> data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        int padding = Utils.dip2px(parent.getContext(),10);
        textView.setPadding(padding,padding,padding,padding);
        return new ViewHolder(textView);
    }

    @Override
    public void onBindHolder(ViewHolder holder, final int position) {
        holder.textView.setText(getItem(position)+"============");
//        holder.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(),position+"",Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(TextView itemView) {
            super(itemView);
            textView = itemView;//(TextView) itemView.findViewById(R.id.tv);
        }
    }
}
