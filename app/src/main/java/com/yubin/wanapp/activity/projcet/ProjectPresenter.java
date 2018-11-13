package com.yubin.wanapp.activity.projcet;

import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.data.model.ProjectDataImpl;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/13
 */
public class ProjectPresenter implements ProjectContract.Presenter {
    private ProjectContract.View mView;
    private ProjectDataImpl projectData;

    public ProjectPresenter(ProjectContract.View view, ProjectDataImpl projectData){
        mView = view;
        mView.setPresenter(this);
        this.projectData = projectData;
    }
    @Override
    public void getProjectListData() {
        projectData.getProjectListData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Projecttree.ProjectBean>>() {
                    @Override
                    public void onNext(List<Projecttree.ProjectBean> value) {
                        mView.showProjectList(value);
                    }

                    @Override
                    public void onError(Throwable e) {

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
