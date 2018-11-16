package com.littleyellow.listhelperdemo;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littleyellow.listhelper.adapter.BaseAdapter;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public class TestAdapter extends BaseAdapter<String,TestAdapter.ViewHolder>{

    boolean isLoop;

    private DisplayMetrics metric;

    public TestAdapter(List data) {
        super(data);
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_test, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindHolder(ViewHolder holder, int position) {
        String msg = getItem(position);
        holder.textView.setText(msg);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
