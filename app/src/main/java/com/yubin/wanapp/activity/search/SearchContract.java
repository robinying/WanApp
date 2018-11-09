package com.yubin.wanapp.activity.search;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.HotkeyData;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

import java.util.List;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public interface SearchContract {
    interface Presenter extends BasePresenter{
        void getQueryList(int page, String key);
        void getHotkey();
    }

    interface View extends BaseView<Presenter>{
        void showQueryArticles(List<ArticleDetailData> list);
        void showHotkey(List<HotkeyData.HotKeyDetail> list);
        void showEmptyView(boolean show);
        void hideImn();
    }
}
