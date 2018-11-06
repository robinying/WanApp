package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.BannerData;
import com.yubin.wanapp.data.BannerDetailData;
import com.yubin.wanapp.data.LoginData;

import java.util.List;

import io.reactivex.Observable;

/**
 * author : Yubin.Ying
 * time : 2018/11/5
 */
public interface GetBannerSource {
    Observable<List<BannerDetailData>> getBannerData();
}
