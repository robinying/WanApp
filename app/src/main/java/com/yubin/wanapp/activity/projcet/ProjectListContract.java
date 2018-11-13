package com.yubin.wanapp.activity.projcet;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public interface ProjectListContract {
    interface Presenter extends BasePresenter{
        void getProjectListData(int page, int cid);
    }

    interface View extends BaseView<Presenter>{
        void showProjectViewPager(List<ArticleDetailData> value);
    }
}
