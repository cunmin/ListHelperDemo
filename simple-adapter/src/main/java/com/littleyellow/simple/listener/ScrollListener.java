package com.littleyellow.simple.listener;

/**
 * Created by 小黄 on 2018/11/20.
 */

public interface ScrollListener {

    void onSelected(int position,int totalSize);

    void onScroll(int dx);

}