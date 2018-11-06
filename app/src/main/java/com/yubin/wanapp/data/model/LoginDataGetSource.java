package com.yubin.wanapp.data.model;

import android.support.annotation.NonNull;

import com.yubin.wanapp.activity.App;
import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.data.LoginDetailData;
import com.yubin.wanapp.data.LoginDetailDataDao;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class LoginDataGetSource implements LoginDataSource {
    @NonNull
    private static LoginDataGetSource INSTANCE;
    private LoginDetailDataDao loginDetailDataDao;

    private LoginDataGetSource(){

    }

    public static LoginDataGetSource getInstance(){
        if (INSTANCE == null) {
            synchronized (LoginDataGetSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoginDataGetSource();
                }
            }
        }
        return INSTANCE;
    }
    @Override
    public Observable<LoginData> getRemoteLoginData(@NonNull String userName, @NonNull String password, String repassword, @NonNull LoginType loginType) {
        Observable<LoginData> loginDataObservable = null;
        if (loginType == LoginType.TYPE_REGISTER) {
            loginDataObservable = RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .register(userName, password, repassword);
        } else if (loginType == LoginType.TYPE_LOGIN) {
            loginDataObservable = RetrofitClient.getInstance()
                    .create(RetrofitService.class)
                    .login(userName, password);
        }
        return loginDataObservable
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<LoginData>() {
                    @Override
                    public void accept(LoginData loginData) throws Exception {
                        if (loginData.getErrorCode() == 0 || loginData.getData() != null) {
                            loginDetailDataDao = App.getContext().getDaoSession().getLoginDetailDataDao();
                            loginDetailDataDao.insertOrReplaceInTx(loginData.getData());
                        }
                    }
                });
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(@NonNull int userid) {
        LoginDetailData localLoginData =
                loginDetailDataDao.queryBuilder().limit(1).where(LoginDetailDataDao.Properties.Id.eq(String.valueOf(userid))).unique();
        return Observable.just(localLoginData);
    }
}
