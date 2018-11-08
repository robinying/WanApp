package com.yubin.wanapp.activity.login;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.model.LoginType;
import com.yubin.wanapp.mvp.BasePresenter;
import com.yubin.wanapp.mvp.BaseView;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public interface LoginContract {
    interface View extends BaseView<Presenter>{
        void showLoginError( String errorMsg);

        void saveUserData(LoginDetailData loginDetailData);

        void showNetworkError();

        void showAutoLogin();

    }

    interface Presenter extends BasePresenter {
        void check(String username, String password,String repassword, @NonNull LoginType loginType);

        void clearLocalData();

        void autoLogin(String username, String password, @NonNull LoginType loginType);

    }

}
