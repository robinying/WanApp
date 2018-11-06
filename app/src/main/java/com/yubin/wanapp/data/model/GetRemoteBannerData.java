package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.BannerData;
import com.yubin.wanapp.data.BannerDetailData;
import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public class GetRemoteBannerData implements GetBannerSource {
    @NonNull
    private static GetRemoteBannerData INSTANCE;

    private GetRemoteBannerData(){

    }

    public static GetRemoteBannerData getInstance(){
        if (INSTANCE == null) {
            synchronized (LoginDataGetSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GetRemoteBannerData();
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public Observable<List<BannerDetailData>> getBannerData() {
        return RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getBanner().filter(new Predicate<BannerData>() {
                    @Override
                    public boolean test(BannerData bannerData) throws Exception {
                        return bannerData.getErrorCode() != -1;
                    }
                })
                //获取的数据类型是BannerData，我们需要的是它内部的BannerDetailData，所以要用到flatMap
                .flatMap(new Function<BannerData, ObservableSource<List<BannerDetailData>>>() {
                    @Override
                    public ObservableSource<List<BannerDetailData>> apply(BannerData bannerData) throws Exception {
                        return Observable.fromIterable(bannerData.getData()).toList().toObservable();
                    }
                });
    }
}
