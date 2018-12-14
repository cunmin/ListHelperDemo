package com.littleyellow.simple.calculate;

/**
 * Created by 小黄 on 2018/11/29.
 */

public class SectionMode implements NumProxy {

    private NumProxy numProxy;

    private int section;

    public SectionMode(NumProxy numProxy,int section){
        this.numProxy =numProxy;
        this.section = section;
    }

    @Override
    public int getInitPosition() {
        if(numProxy instanceof LoopMode){
            int realSize = getRealSize();
            if(0==realSize) {
                return 0;
            }
            int position = getItemCount()/realSize/2*realSize;
            return  position;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if(numProxy instanceof LoopMode){
            return numProxy.getItemCount();
        }else if(numProxy instanceof CommonMode){
            return getRealSize();
        }
        return 0;
    }

    @Override
    public int toPosition(int realPosition) {
        int realSize = getRealSize();
        int realPos =  (realSize + realPosition % realSize)%realSize;
        return realPos;
    }

    @Override
    public int toRealPosition(int position) {
        if(numProxy instanceof LoopMode){
            int realSize = getRealSize();
            if(0==realSize) {
                return 0;
            }
            int realosition = getItemCount()/realSize/2*realSize+position;
            return  realosition;
        }
        return position;
    }

    @Override
    public int getRealSize() {
        int realSize = numProxy.getRealSize();
        return realSize/section+(0==realSize%section?0:1);
    }

    @Override
    public boolean isLoop() {
        return numProxy.isLoop();
    }
}
