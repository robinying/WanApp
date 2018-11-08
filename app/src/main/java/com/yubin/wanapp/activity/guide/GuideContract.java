package com.yubin.wanapp.activity.guide;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.GuideBean;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/7
 */
public interface GuideContract {
    interface Presenter extends BasePresenter{
        void getGuideData();
    }

    interface View extends BaseView<Presenter> {
        boolean isActive();
        void showGuideData(List<GuideBean.DataBean> value);

        void showEmptyView(boolean show);
    }
}
