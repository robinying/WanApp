package com.yubin.wanapp.activity.detail;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.model.TagDetailData;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public class TagDetailPresenter implements TagDetailContract.Presenter {
    private TagDetailContract.View view;
    private TagDetailData tagDetailData;


    public TagDetailPresenter(TagDetailContract.View view, TagDetailData tagDetailData){
        this.view = view;
        view.setPresenter(this);
        this.tagDetailData = tagDetailData;

    }
    @Override
    public void getTagDetail(@NonNull int page, @NonNull int cid) {
        tagDetailData.getArticlesFromCatgory(page,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        view.showTagDetail(value);
                        view.showEmptyView(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showEmptyView(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public int getPageCount() {
        return TagDetailData.getPageTotal();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
