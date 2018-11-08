package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.activity.home.ArticlesDataSource;
import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.ArticleDetailDataDao;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class GetArticlesData implements ArticlesDataSource {

    private final int INDEX = 0;
    private static ArticleDetailDataDao articleDao;

    @NonNull
    public static GetArticlesData INSTANCE;


    public static GetArticlesData getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new GetArticlesData();
            articleDao = App.getContext().getDaoSession().getArticleDetailDataDao();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull boolean clearCache) {
        //!forceUpdate即用户按home键然后再返回我们的APP的情况，这时候直接返回缓存的文章列表
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticles(page)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        return articleData.getErrorCode() != -1;
                    }
                })
                //获取的数据类型是ArticleData，我们需要的是它内部的ArticleDetailData，所以要用到flatMap
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
                                for (ArticleDetailData item : list) {
                                    articleDao.insertOrReplaceInTx(item);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> queryArticles(@NonNull int page, @NonNull String keyWords, @NonNull boolean forceUpdate, @NonNull boolean clearCache) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .queryArticles(page,keyWords)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articlesData) throws Exception {
                        return articlesData.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articleData) throws Exception {
                        return Observable.fromIterable(articleData.getData().getDatas()).toSortedList(new Comparator<ArticleDetailData>() {
                            @Override
                            public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                                return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<ArticleDetailData>>() {
                            @Override
                            public void accept(List<ArticleDetailData> list) throws Exception {
                                for (ArticleDetailData item :list){
                                    articleDao.insertOrReplaceInTx(item);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticlesFromCatgory(@NonNull int page, @NonNull int categoryId, @NonNull boolean forceUpdate, @NonNull boolean clearCache) {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getArticlesFromCatgory(page,categoryId)
                .filter(new Predicate<ArticleData>() {
                    @Override
                    public boolean test(ArticleData articleData) throws Exception {
                        return articleData.getErrorCode() != -1;
                    }
                }).flatMap(new Function<ArticleData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticleData articleData) throws Exception {
                        return Observable.fromIterable(articleData.getData().getDatas()).toSortedList(new Comparator<ArticleDetailData>() {
                            @Override
                            public int compare(ArticleDetailData articleDetailData, ArticleDetailData t1) {
                                return SortDescendUtil.sortArticleDetailData(articleDetailData, t1);
                            }
                        }).toObservable().doOnNext(new Consumer<List<ArticleDetailData>>() {
                            @Override
                            public void accept(List<ArticleDetailData> list) throws Exception {
                                for (ArticleDetailData item :list){
                                    articleDao.insertOrReplaceInTx(item);
                                }
                            }
                        });
                    }
                });
    }
}
