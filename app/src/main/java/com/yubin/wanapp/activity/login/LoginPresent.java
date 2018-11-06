package com.yubin.wanapp.activity.login;

import android.support.annotation.NonNull;

import com.yubin.wanapp.data.LoginData;
import com.yubin.wanapp.data.model.LoginDataGetSource;
import com.yubin.wanapp.data.model.LoginType;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * author : Yubin.Ying
 * time : 2018/11/2
 */
public class LoginPresent implements LoginContract.Presenter {
    @NonNull
    private LoginContract.View mView;
    @NonNull
    private LoginDataGetSource dataGetSource;

    private CompositeDisposable compositeDisposable;
    public LoginPresent(@NonNull LoginContract.View view, @NonNull LoginDataGetSource loginDataGetSource) {
        this.mView = view;
        this.dataGetSource = loginDataGetSource;
        mView.setPresenter(this);
        compositeDisposable = new CompositeDisposable();

    }
    @Override
    public void check(String username, String password, String repassword, @NonNull LoginType loginType) {
        Disposable disposable = dataGetSource.getRemoteLoginData(username, password, repassword, loginType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {

                    @Override
                    public void onNext(LoginData value) {
                        if (value.getErrorCode()==-1){
                            mView.showLoginError(value.getErrorMsg());
                        }else {
                            mView.saveUserData(value.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showNetworkError();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        compositeDisposable.add(disposable);

    }


    @Override
    public void clearLocalData() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();

    }
}
