package com.yubin.wanapp.activity.nav;

import com.yubin.wanapp.data.NaviData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * Created by robin on 2018-11-10.
 */

public interface NavContract {
    interface Presenter extends BasePresenter{
        void getNavData();

    }

    interface  View extends BaseView<Presenter>{
        void showNavData(List<NaviData.NaviBean> list);
        void showEmptyView(boolean show);
        boolean isActive();
    }
}
