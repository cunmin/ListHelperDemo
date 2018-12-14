package com.littleyellow.simple.calculate;

/**
 * Created by 小黄 on 2018/8/23.
 */

public interface NumProxy {

    int getInitPosition();

    int getItemCount();

    int toPosition(int realPosition);

    int toRealPosition(int position);

    int getRealSize();

    boolean isLoop();
}
