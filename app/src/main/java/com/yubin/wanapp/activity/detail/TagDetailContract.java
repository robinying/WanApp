package com.yubin.wanapp.activity.detail;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public interface TagDetailContract {

    interface Presenter extends BasePresenter{
        void getTagDetail(@NonNull int page,@NonNull int cid);
        int getPageCount();
    }

    interface View extends BaseView<Presenter> {
        void showTagDetail(List<ArticleDetailData> list);

        void showEmptyView(boolean show);
    }
}
