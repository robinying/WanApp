package com.yubin.wanapp.activity.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.BannerDetailData;
import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.data.model.GetArticlesData;
import com.yubin.wanapp.data.model.GetRemoteBannerData;
import com.yubin.wanapp.data.model.LoginDataGetSource;
import com.yubin.wanapp.data.model.LoginType;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    @NonNull
    private LoginDataGetSource dataGetSource;
    @NonNull
    private GetRemoteBannerData getRemoteBannerData;
    @NonNull
    private GetArticlesData getArticlesData;
    private CompositeDisposable compositeDisposable;

    public HomePresenter(@NonNull HomeContract.View view, @NonNull LoginDataGetSource loginDataGetSource, @NonNull GetRemoteBannerData getBannerData, @NonNull GetArticlesData articlesData){
        mView = view;
        dataGetSource = loginDataGetSource;
        getRemoteBannerData = getBannerData;
        getArticlesData =articlesData;
        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }
    @Override
    public void getArticles(int page, boolean forceUpdate, boolean clearCache) {
        Disposable disposable = getArticlesData.getArticles(page,forceUpdate,clearCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> value) {
                        if (mView.isActive()){
                            mView.showEmptyView(false);
                            mView.showArticles(value);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.showEmptyView(true);
                            mView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mView.isActive()){
                            mView.setLoadingIndicator(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void getBanner() {
        Disposable disposable = GetRemoteBannerData.getInstance().getBannerData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<BannerDetailData>>() {
                    @Override
                    public void onNext(List<BannerDetailData> value) {
                        if (mView.isActive()) {
                            mView.showBanner(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.hideBanner();
                            mView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mView.isActive()){
                            mView.setLoadingIndicator(false);
                        }
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void autoLogin(String userName, String password) {
        Disposable disposable = LoginDataGetSource.getInstance().getRemoteLoginData(userName, password, null, LoginType.TYPE_LOGIN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {

                    @Override
                    public void onNext(LoginData value) {
                        if (mView.isActive() && value.getErrorCode() == -1) {
                            mView.showAutoLoginFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.showAutoLoginFail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }
}
