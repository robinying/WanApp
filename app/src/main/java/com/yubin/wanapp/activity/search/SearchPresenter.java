package com.yubin.wanapp.activity.search;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.model.GetArticlesData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View mView;
    private GetArticlesData getArticlesData;

    public SearchPresenter(SearchContract.View view, GetArticlesData getArticlesData){
        mView = view;
        mView.setPresenter(this);
        this.getArticlesData = getArticlesData;
    }
    @Override
    public void getQueryList(int page, String key) {
        getArticlesData.queryArticles(page ,key,true,false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if(value!=null&& value.size()>0) {
                            mView.showQueryArticles(value);
                            mView.showEmptyView(false);
                        }else {
                            mView.showEmptyView(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showEmptyView(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
