package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.ArticleDetailData;
import com.yubin.wanapp.data.ProjcetListData;
import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.data.Status;
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
 * time : 2018/11/13
 */
public class ProjectDataImpl {
    @NonNull
    private static ProjectDataImpl INSTANCE;
    private ProjectDataImpl() {

    }

    public static ProjectDataImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProjectDataImpl();
        }
        return INSTANCE;
    }

    public Observable<List<Projecttree.ProjectBean>> getProjectListData(){
        return RetrofitClient.getInstance().create(RetrofitService.class)
                .getProjectData()
                .filter(new Predicate<Projecttree>() {
                    @Override
                    public boolean test(Projecttree projecttree) throws Exception {
                        return projecttree.getErrorCode() != -1;
                    }
                })
                .flatMap(new Function<Projecttree, ObservableSource<List<Projecttree.ProjectBean>>>() {
                    @Override
                    public ObservableSource<List<Projecttree.ProjectBean>> apply(Projecttree projecttree) throws Exception {
                        return Observable.just(projecttree.getData());
                    }
                });
    }

    public Observable<List<ArticleDetailData>> getProjectArticle(int page,int cid){
        return RetrofitClient.getInstance().create(RetrofitService.class)
                .getArticlesFromProject(page, cid)
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
                            }
                        });
                    }
                });
    }

}
