package com.yubin.wanapp.data.model;

import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.FavoriteArticleDetailData;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class SortDescendUtil {

    public static int sortArticleDetailData(ArticleDetailData articleDetailData, ArticleDetailData t1) {
        if (articleDetailData.getPublishTime() > t1.getPublishTime()){
            return -1;
        }else {
            return 1;
        }
    }

    public static int sortFavArticleDetailData(FavoriteArticleDetailData favoriteArticleDetailData, FavoriteArticleDetailData t1) {
        if (favoriteArticleDetailData.getPublishTime() > t1.getPublishTime()){
            return -1;
        }else {
            return 1;
        }
    }
}
