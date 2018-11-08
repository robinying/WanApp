package com.yubin.wanapp.activity.guide;

import com.yubin.wanapp.data.GuideBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : Yubin.Ying
 * time : 2018/11/7
 */
public interface GuideDataSource {
    Observable<List<GuideBean.DataBean>> getGuideData();
}
