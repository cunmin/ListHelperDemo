package com.littleyellow.listhelperdemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class LazyFragment extends Fragment {

    protected boolean isVisible;

    protected boolean isPrepared;

    /**
     * 在这里实现Fragment数据的缓加载.
     * fragment 被设为可见的时候,会调用这个方法
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        if(!isPrepared || !isVisible) {
            return;
        }
        isPrepared = false;
        if(isLazy()) {
            lazyLoad();
        }
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
        inVisible();
    }

    protected abstract void inVisible();


    /**
     * ============发现第一页首次没调用lazyLoad()===========================
     */

    /**
     * onCreateView方法里调用
     */
    public void prepared(){
        isPrepared = true;
        boolean isLazy = isLazy();
        if(!isLazy){
            lazyLoad();
        }
    }

    public static final String NOT_LAZY = "not_Lazy";

    public boolean isLazy(){
        Bundle bundle = getArguments();
        return null==bundle?false:!bundle.getBoolean(NOT_LAZY);
    }

}
