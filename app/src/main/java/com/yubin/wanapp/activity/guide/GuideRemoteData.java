package com.yubin.wanapp.activity.guide;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.GuideBean;
import com.yubin.wanapp.data.Status;
import com.yubin.wanapp.data.model.FavoriteArticleDataImpl;
import com.yubin.wanapp.data.model.SortDescendUtil;
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
 * time : 2018/11/7
 */
public class GuideRemoteData implements GuideDataSource {
    @NonNull
    private static GuideRemoteData INSTANCE;
    private GuideRemoteData() {

    }

    public static GuideRemoteData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GuideRemoteData();
        }
        return INSTANCE;
    }
    @Override
    public Observable<List<GuideBean.DataBean>> getGuideData() {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getGuideData()
                .filter(new Predicate<GuideBean>() {
                    @Override
                    public boolean test(GuideBean guideBean) throws Exception {
                        return guideBean.getErrorCode() != -1;
                    }
                })
                //获取的数据类型是ArticleData，我们需要的是它内部的ArticleDetailData，所以要用到flatMap
                .flatMap(new Function<GuideBean, ObservableSource<List<GuideBean.DataBean>>>() {
                    @Override
                    public ObservableSource<List<GuideBean.DataBean>> apply(GuideBean guideBean) throws Exception {
                        return Observable.just(guideBean.getData());
                    }
                });
    }
}
