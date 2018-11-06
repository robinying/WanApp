package com.yubin.wanapp.data.model;


import android.support.annotation.NonNull;

import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.data.LoginDetailData;

import io.reactivex.Observable;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public interface LoginDataSource {
    Observable<LoginData> getRemoteLoginData(@NonNull String userName, @NonNull String password, String repassword, @NonNull LoginType loginType);
    Observable<LoginDetailData> getLocalLoginData(@NonNull int userid);
}
