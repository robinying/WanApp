package com.yubin.wanapp.activity.home;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.BannerDetailData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public interface HomeContract {
    interface Presenter extends BasePresenter {

        void getArticles(int page, boolean forceUpdate, boolean clearCache);

        void getBanner();

        void autoLogin(String userName, String password);


    }

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setLoadingIndicator(boolean isActive);

        void showArticles(List<ArticleDetailData> list);

        void showEmptyView(boolean toShow);

        void showBanner(List<BannerDetailData> list);

        void hideBanner();

        void showAutoLoginFail();


    }
}
