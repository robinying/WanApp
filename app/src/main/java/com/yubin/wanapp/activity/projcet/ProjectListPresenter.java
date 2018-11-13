package com.yubin.wanapp.activity.projcet;

import android.util.Log;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.model.ProjectDataImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjectListPresenter implements ProjectListContract.Presenter {
    private ProjectListContract.View mView;
    private ProjectDataImpl projectData;

    public ProjectListPresenter(ProjectListContract.View view,ProjectDataImpl projectData){
        mView = view;
        mView.setPresenter(this);
        this.projectData = projectData;
    }
    @Override
    public void getProjectListData(int page, int cid) {
        projectData.getProjectArticle(page,cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        mView.showProjectViewPager(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("robin","e"+e.toString());
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
