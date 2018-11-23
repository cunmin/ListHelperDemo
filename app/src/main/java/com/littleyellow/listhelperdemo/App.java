package com.littleyellow.listhelperdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by 小黄 on 2018/11/23.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
