package com.littleyellow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小黄 on 2018/12/12.
 */

public class MainBean {

    private List<Integer> data;

    private int type;

    public MainBean(int t,int subSize) {
        List<Integer> d = new ArrayList<>();
        for (int i = 0;i<subSize;i++){
            d.add(i);
        }
        data = d;
        type = t;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
