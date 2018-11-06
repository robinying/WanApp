package com.yubin.wanapp.data.model;

import com.yubin.wanapp.data.ArticleDetailData;

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
}
