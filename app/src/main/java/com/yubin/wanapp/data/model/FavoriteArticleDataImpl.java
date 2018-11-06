package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.Status;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public class FavoriteArticleDataImpl implements FavoriteArticleData {

    @NonNull
    private static FavoriteArticleDataImpl INSTANCE;
    private FavoriteArticleDataImpl() {

    }

    public static FavoriteArticleDataImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavoriteArticleDataImpl();
        }
        return INSTANCE;
    }

    @Override
    public Observable<Status> collectArticle(@NonNull int userId, @NonNull int id) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .collectArticle(id)
                .filter(new Predicate<Status>() {
                    @Override
                    public boolean test(Status status) throws Exception {
                        return status.getErrorCode() != -1;
                    }
                })
                .doOnNext(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {
                    }
                });
    }

    @Override
    public Observable<Status> uncollectArticle(@NonNull int userId, @NonNull int originId) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .uncollectArticle(originId)
                .filter(new Predicate<Status>() {
                    @Override
                    public boolean test(Status status) throws Exception {
                        return status.getErrorCode() != -1;
                    }
                })
                .doOnNext(new Consumer<Status>() {
                    @Override
                    public void accept(Status status) throws Exception {

                    }
                });
    }
}
