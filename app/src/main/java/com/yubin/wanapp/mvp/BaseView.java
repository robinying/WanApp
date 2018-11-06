package com.yubin.wanapp.mvp;

import android.view.View;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public interface BaseView<T> {
    void initViews(View view);

    void setPresenter(T presenter);
}
