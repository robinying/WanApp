package com.yubin.wanapp.activity.favourite;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.FavoriteArticleDetailData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
public interface FavouriteContract {
    interface Presenter extends BasePresenter{
        void getFavouriteArticles(@NonNull int page);
    }

    interface View extends BaseView<Presenter>{
        void showFavouriteArticles(List<FavoriteArticleDetailData> list);
        void showEmptyView(boolean show);
    }
}
