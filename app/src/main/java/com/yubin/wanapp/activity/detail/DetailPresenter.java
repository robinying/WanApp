package com.yubin.wanapp.activity.detail;

import android.util.Log;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.data.FavoriteArticleDetailData;
import com.yubin.wanapp.data.FavoriteArticleDetailDataDao;
import com.yubin.wanapp.data.FavoriteArticlesData;
import com.yubin.wanapp.data.model.FavoriteArticleDataImpl;
import com.yubin.wanapp.data.Status;
import com.yubin.wanapp.data.model.SortDescendUtil;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private FavoriteArticleDataImpl mFavoriteArticleDataImpl;
    private FavoriteArticleDetailDataDao favDao;

    public DetailPresenter(DetailContract.View view, FavoriteArticleDataImpl favoriteArticleDataImpl){
        mView = view;
        mView.setPresenter(this);
        mFavoriteArticleDataImpl = favoriteArticleDataImpl;
        favDao = App.getContext().getDaoSession().getFavoriteArticleDetailDataDao();

    }
    @Override
    public void collectArticle(int userId, long id) {
            mFavoriteArticleDataImpl.collectArticle(userId, id)
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
    }

    @Override
    public void uncollectArticle(int userId, long originId) {
        mFavoriteArticleDataImpl.uncollectArticle(userId, originId)
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

    }

    @Override
    public void updateFavArticle() {
        Observable<List<FavoriteArticleDetailData>> list = RetrofitClient.getInstance().create(RetrofitService.class)
                .getFavoriteArticles(0)
                .filter(new Predicate<FavoriteArticlesData>() {
                    @Override
                    public boolean test(FavoriteArticlesData favoriteArticlesData) throws Exception {
                        return favoriteArticlesData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<FavoriteArticlesData, ObservableSource<List<FavoriteArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<FavoriteArticleDetailData>> apply(FavoriteArticlesData favoriteArticlesData) throws Exception {
                        return Observable.fromIterable(favoriteArticlesData.getData().getDatas()).toSortedList(new Comparator<FavoriteArticleDetailData>() {
                            @Override
                            public int compare(FavoriteArticleDetailData favoriteArticleDetailData, FavoriteArticleDetailData t1) {
                                return SortDescendUtil.sortFavArticleDetailData(favoriteArticleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<FavoriteArticleDetailData>>() {
                            @Override
                            public void accept(List<FavoriteArticleDetailData> list) throws Exception {
                                for (FavoriteArticleDetailData item : list) {
                                    favDao.insertOrReplace(item);
                                }
                            }
                        });
                    }
                });
        list.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<FavoriteArticleDetailData>>() {
                    @Override
                    public void onNext(List<FavoriteArticleDetailData> value) {
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
