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
    public int getPosition(int realPosition) {
        int realSize = getRealSize();
        int realPos =  (realSize + realPosition % realSize)%realSize;
//        int realPos = numProxy.getPosition(realPosition);
        return realPos;//realPos*section+(0==realPos%section?0:1);
    }

    @Override
    public int getRealSize() {
        int realSize = numProxy.getRealSize();
        return realSize/section+(0==realSize%section?0:1);
    }
}
