package com.littleyellow.listhelperdemo;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.littleyellow.simple.adapter.SimpleAdapter;

import java.util.List;

/**
 * Created by 小黄 on 2018/8/23.
 */

public class TestAdapter extends SimpleAdapter<String,TestAdapter.ViewHolder> {

    boolean isLoop;

    private DisplayMetrics metric;

    private View.OnTouchListener touchListener;

    public TestAdapter(List data,View.OnTouchListener touchListener) {
        super(data);
        this.touchListener = touchListener;
    }

    @Override
    public ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_test, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(position+"");
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.itemView.setTranslationX(v.getWidth()/2);
                holder.textView.setText(position+"-"+holder.itemView.getLeft());
                Toast.makeText(v.getContext(),position+"",Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onBindSectionHolder(ViewHolder holder, final int startPosition, int endPosition) {
        String text = startPosition==endPosition?startPosition+"":startPosition+"~"+endPosition;
        holder.textView.setText(text+"");
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),startPosition+"",Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
