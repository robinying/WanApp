package com.yubin.wanapp.activity.detail;

import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public interface DetailContract {
    interface Presenter extends BasePresenter {
        void collectArticle(int userId, int originId);

        void uncollectArticle(int userId, int originId);
    }

    interface View extends BaseView<Presenter> {
        void showCollectStatus(boolean isSuccess);

        void showUnCollectStatus(boolean isSuccess);

        boolean isActive();

        void saveFavoriteState(boolean isFavorite);


    }
}
