package com.yubin.wanapp.retrofit;

import com.yubin.wanapp.data.ArticleData;
import com.yubin.wanapp.data.BannerData;
import com.yubin.wanapp.data.FavoriteArticlesData;
import com.yubin.wanapp.data.GuideBean;
import com.yubin.wanapp.data.HotkeyData;
import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.data.NaviData;
import com.yubin.wanapp.data.ProjcetListData;
import com.yubin.wanapp.data.Projecttree;
import com.yubin.wanapp.data.Status;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public interface RetrofitService {
    @FormUrlEncoded
    @POST(Api.LOGIN)
    Observable<LoginData> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST(Api.REGISTER)
    Observable<LoginData> register(@Field("username") String username, @Field("password") String password,@Field("repassword") String repassword);

    @GET(Api.BANNER)
    Observable<BannerData> getBanner();

    //获取首页文章
    @GET(Api.ARTICLE_LIST + "{page}/json")
    Observable<ArticleData> getArticles(@Path("page") int page);

    @POST(Api.QUERY_ARTICLES + "{page}/json")
    Observable<ArticleData> queryArticles(@Path("page") int page, @Query("k") String k);

    @GET(Api.ARTICLE_LIST + "{page}/json")
    Observable<ArticleData> getArticlesFromCatgory(@Path("page") int page, @Query("cid") int cid);

    @POST(Api.COLLECT_ARTICLE+"{id}/json")
    Observable<Status> collectArticle(@Path("id") long id);

    @POST(Api.CANCEL_COLLECTING_ARTICLE + "{originId}/json")
    Observable<Status> uncollectArticle(@Path("originId") long originId);

    //获取收藏文章的列表
    @GET(Api.GET_FAVORITE_ARTICLES + "{page}/json")
    Observable<FavoriteArticlesData> getFavoriteArticles(@Path("page") int page);

    @GET(Api.GUIDE_TREE)
    Observable<GuideBean> getGuideData();

    @GET(Api.HOT_KEY)
    Observable<HotkeyData> getHotkey();

    @GET(Api.NAV_TREE)
    Observable<NaviData> getNavdata();

    @GET(Api.PROJECT_TREE)
    Observable<Projecttree> getProjectData();

    @GET(Api.ARTICLE_LIST + "{page}/json")
    Observable<ArticleData> getArticlesFromProject(@Path("page") int page, @Query("cid") int cid);



}
