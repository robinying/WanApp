package com.yubin.wanapp.activity.home;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.ArticleDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public interface ArticlesDataSource {

    Observable<List<ArticleDetailData>> getArticles(@NonNull int page, @NonNull boolean forceUpdate, @NonNull boolean clearCache);

    Observable<List<ArticleDetailData>> queryArticles(@NonNull int page, @NonNull String keyWords, @NonNull boolean forceUpdate , @NonNull boolean clearCache);

    Observable<List<ArticleDetailData>> getArticlesFromCatgory(@NonNull int page, @NonNull int categoryId , @NonNull boolean forceUpdate, @NonNull boolean clearCache);
}
