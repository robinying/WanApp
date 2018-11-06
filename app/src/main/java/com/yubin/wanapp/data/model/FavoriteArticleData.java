package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.Status;

import io.reactivex.Observable;
import retrofit2.http.Path;

/**
 * author : Yubin.Ying
 * time : 2018/11/6
 */
public interface FavoriteArticleData {
    Observable<Status> collectArticle(@NonNull int userId, @NonNull int id);

    Observable<Status> uncollectArticle(@NonNull int userId, @NonNull int originId);
}
