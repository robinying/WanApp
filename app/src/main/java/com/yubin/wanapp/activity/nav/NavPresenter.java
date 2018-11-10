package com.yubin.wanapp.activity.nav;

import com.yubin.wanapp.data.NaviData;
import com.yubin.wanapp.retrofit.RetrofitClient;
import com.yubin.wanapp.retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by robin on 2018-11-10.
 */

public class NavPresenter implements NavContract.Presenter {

    private NavContract.View mView;

    public NavPresenter(NavContract.View view){
        mView = view;
        mView.setPresenter(this);
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void getNavData() {
        Observable<List<NaviData.NaviBean>> listObservable = RetrofitClient.getInstance()
                .create(RetrofitService.class)
                .getNavdata()
                .filter(new Predicate<NaviData>() {
                    @Override
                    public boolean test(NaviData naviData) throws Exception {
                        return naviData.getErrorCode()!= -1;
                    }
                })
                .flatMap(new Function<NaviData, ObservableSource<List<NaviData.NaviBean>>>() {
                    @Override
                    public ObservableSource<List<NaviData.NaviBean>> apply(NaviData naviData) throws Exception {
                        return Observable.just(naviData.getData());
                    }
                });
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<NaviData.NaviBean>>() {
                    @Override
                    public void onNext(List<NaviData.NaviBean> value) {
                        mView.showNavData(value);
                        mView.showEmptyView(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showEmptyView(true);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
