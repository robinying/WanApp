package com.yubin.wanapp.activity.guide;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.GuideBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/7
 */
public class GuidePresenter implements GuideContract.Presenter {
    private GuideRemoteData guideRemoteData;
    private GuideContract.View mView;

    public GuidePresenter(GuideContract.View view,GuideRemoteData guideRemoteData){
        mView =view;
        mView.setPresenter(this);
        this.guideRemoteData = guideRemoteData;

    }
    @Override
    public void getGuideData() {
        guideRemoteData.getGuideData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<GuideBean.DataBean>>() {
                    @Override
                    public void onNext(List<GuideBean.DataBean> value) {
                        if (mView.isActive()) {
                            mView.showEmptyView(false);
                            mView.showGuideData(value);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.showEmptyView(true);
                        }
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
