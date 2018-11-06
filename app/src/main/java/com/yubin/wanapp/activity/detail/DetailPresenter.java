package com.yubin.wanapp.activity.detail;

import com.yubin.wanapp.data.model.FavoriteArticleDataImpl;
import com.yubin.wanapp.data.Status;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private FavoriteArticleDataImpl mFavoriteArticleDataImpl;
    private CompositeDisposable compositeDisposable;

    public DetailPresenter(DetailContract.View view, FavoriteArticleDataImpl favoriteArticleDataImpl){
        mView = view;
        mView.setPresenter(this);
        mFavoriteArticleDataImpl = favoriteArticleDataImpl;
        compositeDisposable = new CompositeDisposable();

    }
    @Override
    public void collectArticle(int userId, int id) {
        Disposable  disposable = mFavoriteArticleDataImpl.collectArticle(userId, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Status>() {

                    @Override
                    public void onNext(Status value) {
                        if (mView.isActive()) {
                            mView.showCollectStatus(true);
                            mView.saveFavoriteState(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.showCollectStatus(false);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    public void uncollectArticle(int userId, int originId) {
        Disposable disposable = mFavoriteArticleDataImpl.uncollectArticle(userId, originId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Status>() {

                    @Override
                    public void onNext(Status value) {
                        if (mView.isActive()) {
                            mView.showUnCollectStatus(true);
                            mView.saveFavoriteState(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView.isActive()) {
                            mView.showUnCollectStatus(false);
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
