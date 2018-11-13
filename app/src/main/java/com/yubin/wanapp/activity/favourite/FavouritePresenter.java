package com.yubin.wanapp.activity.favourite;

import android.support.annotation.NonNull;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.FavoriteArticleDetailData;
import com.yubin.wanapp.data.FavoriteArticleDetailDataDao;
import com.yubin.wanapp.data.FavoriteArticlesData;
import com.yubin.wanapp.data.model.SortDescendUtil;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/9
 */
public class FavouritePresenter implements FavouriteContract.Presenter {
    private FavouriteContract.View mView;
    private FavoriteArticleDetailDataDao favDao;

    public FavouritePresenter(FavouriteContract.View view){
        mView = view;
        mView.setPresenter(this);
        favDao = App.getContext().getDaoSession().getFavoriteArticleDetailDataDao();
    }
    @Override
    public void getFavouriteArticles(@NonNull int page) {
        Observable<List<FavoriteArticleDetailData>> list =RetrofitClient.getInstance().create(RetrofitService.class)
                .getFavoriteArticles(page)
                .filter(new Predicate<FavoriteArticlesData>() {
                    @Override
                    public boolean test(FavoriteArticlesData favoriteArticlesData) throws Exception {
                        return favoriteArticlesData.getErrorCode() == 0;
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
                        mView.showFavouriteArticles(value);
                        mView.showEmptyView(false);
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
