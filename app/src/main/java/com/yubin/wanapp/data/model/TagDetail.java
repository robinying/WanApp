package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : Yubin.Ying
 * time : 2018/11/8
 */
public interface TagDetail {
    Observable<List<ArticleDetailData>> getArticlesFromCatgory(@NonNull int page, @NonNull int categoryId);
}
