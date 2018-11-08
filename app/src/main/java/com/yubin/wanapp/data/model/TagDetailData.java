package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public class TagDetailData implements TagDetail {
    @NonNull
    public static TagDetailData INSTANCE;


    public static TagDetailData getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new TagDetailData();
        }
        return INSTANCE;
    }

    private static int pageTotal = 0;
    @Override
    public Observable<List<ArticleDetailData>> getArticlesFromCatgory(@NonNull int page, @NonNull int categoryId) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticlesFromCatgory(page, categoryId)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        pageTotal = articleData.getData().getPageCount();
                        return articleData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articlesData) throws Exception {
                        return Observable.fromIterable(articlesData.getData().getDatas()).toSortedList(new Comparator<ArticleDetailData>() {
                            @Override
                            public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                                return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<ArticleDetailData>>() {
                            @Override
                            public void accept(List<ArticleDetailData> list) throws Exception {

                            }
                        });
                    }
                });
    }

    public static int getPageTotal(){
        return pageTotal;
    }
}
